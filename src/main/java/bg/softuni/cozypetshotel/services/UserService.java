package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import bg.softuni.cozypetshotel.models.dtos.EditUserDetailsDTO;
import bg.softuni.cozypetshotel.models.dtos.RegisterDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.session.AppUserDetails;
import java.util.Optional;

public interface UserService {
    public void register(RegisterDTO registerDTO);

    public Optional<AppUserDetails> getCurrentUser();

    public UserDTO findByEmail(String email);

    public void updateBookings(UserDTO userDTO);

    public void editEmail(Long id, String newEmail);

    public void editPassword(Long userId, String currentPassword, String newPassword);

    public void editUsername(Long id, String username);
    public void editLastName(Long userId, String lastName);

    public void editContactNumber(Long userId, String contactNumber);

}

