package bg.softuni.cozypetshotel.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cats")
public class Cat extends BaseAnimal {
    public Cat() {
    }
}
