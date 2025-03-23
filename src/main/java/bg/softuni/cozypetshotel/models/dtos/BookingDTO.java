package bg.softuni.cozypetshotel.models.dtos;

import java.time.LocalDate;

public class BookingDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String petType;
    private int numberOfPets;
    private String petName;
    private String breed;
    private String additionalInformation;

    public BookingDTO() {
    }

    public Long getId() {
        return id;
    }

    public BookingDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public BookingDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public BookingDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BookingDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public BookingDTO setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public BookingDTO setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public String getPetType() {
        return petType;
    }

    public BookingDTO setPetType(String petType) {
        this.petType = petType;
        return this;
    }

    public int getNumberOfPets() {
        return numberOfPets;
    }

    public BookingDTO setNumberOfPets(int numberOfPets) {
        this.numberOfPets = numberOfPets;
        return this;
    }

    public String getPetName() {
        return petName;
    }

    public BookingDTO setPetName(String petName) {
        this.petName = petName;
        return this;
    }

    public String getBreed() {
        return breed;
    }

    public BookingDTO setBreed(String breed) {
        this.breed = breed;
        return this;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public BookingDTO setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }
}
