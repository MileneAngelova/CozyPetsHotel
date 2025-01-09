package bg.softuni.cozypetshotel.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(name = "check-in", nullable = false)
    private LocalDate CheckIn;

    @Column(name = "check-out", nullable = false)
    private LocalDate CheckOut;

    @Column(nullable = false)
    private int numberOfPets;

    @Column(name = "additional_information")
    private String additionalInformation;

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public Booking setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public Booking setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Booking setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Booking setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public LocalDate getCheckIn() {
        return CheckIn;
    }

    public Booking setCheckIn(LocalDate checkIn) {
        CheckIn = checkIn;
        return this;
    }

    public LocalDate getCheckOut() {
        return CheckOut;
    }

    public Booking setCheckOut(LocalDate checkOut) {
        CheckOut = checkOut;
        return this;
    }

    public int getNumberOfPets() {
        return numberOfPets;
    }

    public Booking setNumberOfPets(int numberOfPets) {
        this.numberOfPets = numberOfPets;
        return this;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public Booking setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }
}
