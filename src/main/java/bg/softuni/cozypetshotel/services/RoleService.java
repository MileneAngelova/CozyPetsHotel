package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;
import bg.softuni.cozypetshotel.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void seedRoles() {
        if (roleRepository.count() == 0) {
            Arrays.stream(RoleNameEnum.values())
                    .forEach(roleNameEnum -> {
                        Role role = new Role();
                        role.setName(roleNameEnum);
                        this.roleRepository.save(role);
                    });
        }
    }
}
