package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.dtos.RegisterDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.session.AppUserDetails;

import java.util.Optional;

public interface UserService {
    void register(RegisterDTO registerDTO);

    Optional<AppUserDetails> getCurrentUser();

    UserDTO findByEmail(String email);

    void editEmail(String email, String newEmail);

    void editPassword(String email, String currentPassword, String newPassword);

    void editUsername(String email, String username);

    void editLastName(String email, String lastName);

    void editContactNumber(String email, String contactNumber);

    boolean isValidPassword(String password);

    UserDTO convertToDTO(User user);
}

