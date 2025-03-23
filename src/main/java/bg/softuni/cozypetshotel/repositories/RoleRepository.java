package bg.softuni.cozypetshotel.repositories;

import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(RoleNameEnum role);
}
