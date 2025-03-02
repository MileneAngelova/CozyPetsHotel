package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;
import bg.softuni.cozypetshotel.repositories.RoleRepository;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import bg.softuni.cozypetshotel.web.exceptions.RoleDoesNotExistsException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void seedRoles() {
        if (roleRepository.count() == 0) {
            Arrays.stream(RoleNameEnum.values())
                    .forEach(roleNameEnum -> {
                        Role role = new Role();
                        role.setRole(roleNameEnum);
                        this.roleRepository.save(role);
                    });
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Role> findAllRolesOfUser() {
        return roleRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void addRoleToUser(User user, RoleNameEnum roleName) {
        Role role = roleRepository.findByRole(roleName);
            user.getRoles().add(role);
            userRepository.save(user);
        }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void removeRoleFromUser(User user, RoleNameEnum roleName) {
        Role roleToRemove = user.getRoles().stream()
                .filter(r -> r.getRole().equals(roleName))
                .findFirst()
                .orElse(null);

        if (roleToRemove != null) {
            user.getRoles().remove(roleToRemove);
            userRepository.save(user);
        } else {
            throw new RoleDoesNotExistsException("User does not have this role.");
        }
    }

}
