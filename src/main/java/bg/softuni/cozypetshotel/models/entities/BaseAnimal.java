package bg.softuni.cozypetshotel.models.entities;

import jakarta.persistence.*;

@MappedSuperclass
public class BaseAnimal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String breed;

    @Column(nullable = false)
    private String petName;

    @Column(nullable = false)

    private int petAge;

    public Long getId() {
        return id;
    }

    public BaseAnimal setId(Long id) {
        this.id = id;
        return this;
    }
    public String getBreed() {
        return breed;
    }
    public BaseAnimal setBreed(String breed) {
        this.breed = breed;
        return this;
    }

    public String getPetName() {
        return petName;
    }

    public BaseAnimal setPetName(String petName) {
        this.petName = petName;
        return this;
    }

    public int getPetAge() {
        return petAge;
    }

    public BaseAnimal setPetAge(int petAge) {
        this.petAge = petAge;
        return this;
    }
}
