package bg.softuni.cozypetshotel.models.views;

import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;

import java.util.List;

public class UserViewModel {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<RoleNameEnum> roles;
    private Boolean isActive;

    public UserViewModel() {
    }

    public Long getId() {
        return id;
    }

    public UserViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<RoleNameEnum> getRoles() {
        return roles;
    }

    public UserViewModel setRoles(List<RoleNameEnum> roles) {
        this.roles = roles;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public UserViewModel setActive(Boolean active) {
        isActive = active;
        return this;
    }
}
