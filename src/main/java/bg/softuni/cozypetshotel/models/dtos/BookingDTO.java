package bg.softuni.cozypetshotel.models.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class BookingDTO {
    @NotBlank(message = "Please enter your name!")
    private String fullName;

    @Email(message = "Email format is not valid!")
    @NotBlank(message = "This field can not be empty!")
    private String email;

    @NotBlank(message = "This field can not be empty!")
    private String phoneNumber;

    @NotBlank(message = "This field can not be empty!")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @FutureOrPresent
    private LocalDate CheckIn;

    @NotBlank(message = "This field can not be empty!")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @FutureOrPresent
    private LocalDate CheckOut;

    @NotNull(message = "Choose between 1 and 5")
    @Positive
    private int numberOfPets;

    @Column(name = "additional_information")
    private String additionalInformation;

    public BookingDTO() {
    }

    public String getFullName() {
        return fullName;
    }

    public BookingDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BookingDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BookingDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public LocalDate getCheckIn() {
        return CheckIn;
    }

    public BookingDTO setCheckIn(LocalDate checkIn) {
        CheckIn = checkIn;
        return this;
    }

    public LocalDate getCheckOut() {
        return CheckOut;
    }

    public BookingDTO setCheckOut(LocalDate checkOut) {
        CheckOut = checkOut;
        return this;
    }

    public int getNumberOfPets() {
        return numberOfPets;
    }

    public BookingDTO setNumberOfPets(int numberOfPets) {
        this.numberOfPets = numberOfPets;
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
