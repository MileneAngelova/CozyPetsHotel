package bg.softuni.cozypetshotel.models.dtos;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class LoginDTO {
    @Email(message = "Wrong email format ")
    private String email;

    @Size(min = 4, max = 30)
    private String password;
    @AssertTrue(message = "Your account was blocked!")
    private boolean isActive;

    public LoginDTO() {
    }

    public String getEmail() {
        return email;
    }

    public LoginDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public LoginDTO setActive(boolean active) {
        isActive = active;
        return this;
    }
}
