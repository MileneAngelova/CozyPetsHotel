package bg.softuni.cozypetshotel.repositories;

import bg.softuni.cozypetshotel.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
