package bg.softuni.cozypetshotel.models.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    private Integer age;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private String contactNumber;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Booking> activeBookings;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Booking> expiredBookings;

    public User() {
        this.roles = new HashSet<>();
        this.activeBookings = new ArrayList<>();
        this.isActive = true;
    }

    public String getFirstname() {
        return firstName;
    }

    public User setFirstname(String firstname) {
        this.firstName = firstname;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return contactNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.contactNumber = phoneNumber;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public User setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public List<Booking> getActiveBookings() {
        return activeBookings;
    }

    public User setActiveBookings(List<Booking> activeBookings) {
        this.activeBookings = activeBookings;
        return this;
    }

    public List<Booking> getExpiredBookings() {
        return expiredBookings;
    }

    public User setExpiredBookings(List<Booking> expiredBookings) {
        this.expiredBookings = expiredBookings;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public User setActive(boolean active) {
        isActive = active;
        return this;
    }
}
