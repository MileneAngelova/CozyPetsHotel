package bg.softuni.cozypetshotel.models.dtos;

import bg.softuni.cozypetshotel.validation.FieldsMatch;
import bg.softuni.cozypetshotel.validation.UniqueUserEmail;
import bg.softuni.cozypetshotel.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@FieldsMatch(first = "password", second = "confirmPassword", message = "Passwords do not match!")
public class RegisterDTO {
//    @NotBlank(message = "This field must not be empty!")
//    @UniqueUsername(message = "Username already exists! Choose another one!")
//    @Size(min = 3, max = 50)
//    private String username;

    @NotBlank(message = "Enter your first name!")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "Enter your last name!")
    @Size(min = 2, max = 80)
    private String lastName;

    @NotBlank(message = "Enter your email")
    @Email(message = "Email format is not valid!")
    @UniqueUserEmail(message = "This email is already registered!")
    private String email;

    @NotBlank(message = "Choose a password!")
    @Size(min = 4, max = 30, message = "Password must be between 4 and 30 symbols long!")
    private String password;

    @NotBlank
    @Size(min = 4, max = 30)
    private String confirmPassword;

    public RegisterDTO() {
    }

//    public String getUsername() {
//        return username;
//    }
//
//    public RegisterDTO setUsername(String username) {
//        this.username = username;
//        return this;
//    }


    public String getFirstName() {
        return firstName;
    }

    public RegisterDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public RegisterDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public RegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
