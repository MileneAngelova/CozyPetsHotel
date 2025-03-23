package bg.softuni.cozypetshotel.config;

import bg.softuni.cozypetshotel.services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig implements CommandLineRunner {

    private final RoleService roleService;

    public ApplicationConfig(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.roleService.seedRoles();
    }
}
