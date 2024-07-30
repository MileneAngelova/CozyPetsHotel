package bg.softuni.cozypetshotel.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "dogs")
public class Dog extends BaseAnimal {
    public Dog() {
    }
}
