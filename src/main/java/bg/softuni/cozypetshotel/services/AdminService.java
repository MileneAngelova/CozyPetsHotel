package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;
import bg.softuni.cozypetshotel.models.views.UserViewModel;
import bg.softuni.cozypetshotel.repositories.RoleRepository;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import bg.softuni.cozypetshotel.web.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AdminService(UserRepository userRepository, RoleRepository roleRepository, UserService userService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        userRepository.delete(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userService::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<UserViewModel> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 10);
        return userRepository.findAll(pageable)
                .map(user -> {
                    UserViewModel userViewModel = modelMapper.map(user, UserViewModel.class);
                    List<RoleNameEnum> roles = user.getRoles()
                            .stream()
                            .map(role -> RoleNameEnum.valueOf(role.getRole().name()))
                            .toList();
                    userViewModel.setRoles(roles);
                    return userViewModel;
                });
    }

    public void addRoleAdminToUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        Role roleAdmin = roleRepository.findByRole(RoleNameEnum.ADMIN);

        if (user.getRoles().contains(roleAdmin)) {
            throw new RuntimeException("User already has role Admin!");
        }
        user.getRoles().add(roleAdmin);
        this.userRepository.save(user);
    }

    public void removeRoleAdminFromUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        Set<Role> roles = user.getRoles();
        roles.removeIf(role -> role.getId() == 1);
    }

    public void activateUserAccount(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    String userEmail = user.getEmail();
                    if (userEmail.contains("disabled")) {
                        user.setEmail(userEmail.substring(0, userEmail.length() - 8));
                    }
                    user.setActive(true);
                    userRepository.save(user);
                });
    }

    public void blockUser(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    String userEmail = user.getEmail();
                    user.setActive(false);
                    user.setEmail(userEmail + "disabled");
                    userRepository.save(user);
                });
    }
}
