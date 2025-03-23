package bg.softuni.cozypetshotel.models.dtos;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AddBookingDTO {
    private Long id;
    @NotBlank(message = "Please enter your first name!")
    private String firstName;

    @NotBlank(message = "Please enter your last name!")
    private String lastName;

    @Email(message = "Email format is not valid!")
    @NotBlank(message = "This field can not be empty!")
    private String email;

    @NotBlank(message = "This field can not be empty!")
    private String contactNumber;

    @NotNull(message = "This field can not be empty!")
    @FutureOrPresent(message = "The date can not be in the past!")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate checkIn;

    @NotNull(message = "This field can not be empty!")
    @FutureOrPresent(message = "Checkout can not be earlier than the checkIn date!")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate checkOut;

    @NotBlank(message = "Please select one option!")
    private String petType;

    @NotNull(message = "Choose between 1 and 5")
    @Positive
    private int numberOfPets;

    @NotBlank(message = "This field can not be empty!")
    private String petName;

    @NotBlank(message = "This field can not be empty!")
    private String breed;

    @Size(max = 2000)
    private String additionalInformation;

    public AddBookingDTO() {
    }

    public Long getId() {
        return id;
    }

    public AddBookingDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AddBookingDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AddBookingDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AddBookingDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public AddBookingDTO setContactNumber(String phoneNumber) {
        this.contactNumber = phoneNumber;
        return this;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public AddBookingDTO setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public AddBookingDTO setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public String getPetType() {
        return petType;
    }

    public AddBookingDTO setPetType(String petType) {
        this.petType = petType;
        return this;
    }

    public int getNumberOfPets() {
        return numberOfPets;
    }

    public AddBookingDTO setNumberOfPets(int numberOfPets) {
        this.numberOfPets = numberOfPets;
        return this;
    }

    public String getPetName() {
        return petName;
    }

    public AddBookingDTO setPetName(String petName) {
        this.petName = petName;
        return this;
    }

    public String getBreed() {
        return breed;
    }

    public AddBookingDTO setBreed(String breed) {
        this.breed = breed;
        return this;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public AddBookingDTO setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    @Override
    public String toString() {
        return "AddBookingDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", petType='" + petType + '\'' +
                ", numberOfPets=" + numberOfPets +
                ", petName='" + petName + '\'' +
                ", breed='" + breed + '\'' +
                ", additionalInformation='" + additionalInformation + '\'' +
                '}';
    }
}
