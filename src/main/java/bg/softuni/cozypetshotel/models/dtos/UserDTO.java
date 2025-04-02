package bg.softuni.cozypetshotel.models.dtos;

import bg.softuni.cozypetshotel.models.entities.Booking;
import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.validation.UniqueUserEmail;
import bg.softuni.cozypetshotel.validation.UniqueUsername;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static bg.softuni.cozypetshotel.utils.Constants.PASSWORD_REGEX;

public class UserDTO {
    private UUID uuid;
    private String firstName;
    @Size(min = 2, max = 50)
    private String lastName;
    @Pattern(regexp = PASSWORD_REGEX, message = "Password must be between 4 and 30 symbols long!")
    private String password;
    @UniqueUsername(message = "Username already exists! Choose another one!")
    private String username;
    @Email(message = "Email format is not valid!")
    @UniqueUserEmail(message = "This email is already registered!")
    private String email;
    private Set<Role> roles;
    private String contactNumber;
    private boolean isActive;

    public UserDTO() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public UserDTO setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public UserDTO setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public UserDTO setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserDTO setActive(boolean active) {
        isActive = active;
        return this;
    }
}
