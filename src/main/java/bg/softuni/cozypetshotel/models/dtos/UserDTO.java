package bg.softuni.cozypetshotel.models.dtos;

public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private String email;


    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public UserDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
