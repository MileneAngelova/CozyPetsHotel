package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
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
//    public UserDTO convertToDTO(User user);
}

