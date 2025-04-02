package bg.softuni.cozypetshotel.services.impl;

import bg.softuni.cozypetshotel.models.dtos.RegisterDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;
import bg.softuni.cozypetshotel.repositories.RoleRepository;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import bg.softuni.cozypetshotel.services.UserService;
import bg.softuni.cozypetshotel.session.AppUserDetails;
import bg.softuni.cozypetshotel.validation.FieldsMatch;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static bg.softuni.cozypetshotel.utils.Constants.PASSWORD_REGEX;

@Service
@FieldsMatch(first = "password", second = "confirmPassword", message = "Passwords do not match!")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        Optional<User> byEmail = this.userRepository.findByEmail(registerDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new IllegalArgumentException("Email " + registerDTO.getEmail() + " is already registered!");
        }

        User newUser = this.modelMapper.map(registerDTO, User.class);
        Role newUserRole = this.roleRepository.findByRole(RoleNameEnum.USER);
        newUser.setPassword(this.passwordEncoder.encode(registerDTO.getPassword()));
        newUser.getRoles().add(newUserRole);
        newUser.setActive(true);

        userRepository.save(newUser);
    }

    @Override
    public Optional<AppUserDetails> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof AppUserDetails appUserDetails) {
            return Optional.of(appUserDetails);
        }
        return Optional.empty();
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " was not found!"));
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void editEmail(String email, String newEmail) {
        if (userRepository.findByEmail(newEmail).isPresent()) {
            throw new IllegalArgumentException(("User with email " + newEmail + " already exists!"));
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    @Override
    public void editPassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password!");
        }
        if (!isValidPassword(newPassword)) {
            throw new IllegalArgumentException("Password must be between 4 and 30 symbols long!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void editUsername(String email, String username) {
        if (this.userRepository.findByEmail(email).isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if (this.userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("username is already taken!");
        }
        user.setUsername(username);
        userRepository.save(user);
    }

    @Override
    public void editLastName(String email, String newLastName) {
        if (this.userRepository.findByEmail(email).isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        user.setLastName(newLastName);
        userRepository.save(user);
    }

    @Override
    public void editContactNumber(String email, String newContactNumber) {
        if (this.userRepository.findByEmail(email).isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        user.setContactNumber(newContactNumber);
        userRepository.save(user);
    }

    @Override
    public boolean isValidPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    @Override
    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(user.getUuid());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setRoles(user.getRoles());
        userDTO.setActive(user.isActive());
        return userDTO;
    }
}

