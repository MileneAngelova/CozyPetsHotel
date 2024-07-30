package bg.softuni.cozypetshotel.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false, name = "street_name")
    private String streetName;
    @Column(nullable = false, name = "street_number")
    private String streetNumber;
    @Column(nullable = false, name = "post_code")
    private String postCode;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public Address setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public Address setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public Address setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public String getPostCode() {
        return postCode;
    }

    public Address setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }
}
