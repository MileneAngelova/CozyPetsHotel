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
}
