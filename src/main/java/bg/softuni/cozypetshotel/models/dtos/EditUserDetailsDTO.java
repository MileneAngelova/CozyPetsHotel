package bg.softuni.cozypetshotel.models.dtos;

import bg.softuni.cozypetshotel.validation.FieldsMatch;
import bg.softuni.cozypetshotel.validation.UniqueUserEmail;
import bg.softuni.cozypetshotel.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@FieldsMatch(first = "password", second = "confirmPassword", message = "Passwords do not match!")
public class EditUserDetailsDTO {
    private Long id;

    @NotBlank(message = "This field must not be empty!")
    @UniqueUsername(message = "Username already exists! Choose another one!")
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank(message = "Enter your email")
    @Email(message = "Email format is not valid!")
    @UniqueUserEmail(message = "This email is already registered!")
    private String email;

    @NotBlank(message = "Enter your last name!")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters!")
    private String lastName;

    @NotBlank(message = "Choose a password!")
    @Size(min = 4, max = 30, message = "Password must be between 4 and 30 symbols long!")
    private String currentPassword;

    @NotBlank(message = "Choose a password!")
    @Size(min = 4, max = 30, message = "Password must be between 4 and 30 symbols long!")
    private String newPassword;

    public EditUserDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public EditUserDetailsDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public EditUserDetailsDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public EditUserDetailsDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public EditUserDetailsDTO setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public EditUserDetailsDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }
}