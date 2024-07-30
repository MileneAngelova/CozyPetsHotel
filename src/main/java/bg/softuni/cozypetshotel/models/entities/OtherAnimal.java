package bg.softuni.cozypetshotel.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "other_animals")
public class OtherAnimal extends BaseAnimal {
    public OtherAnimal() {
    }
}
