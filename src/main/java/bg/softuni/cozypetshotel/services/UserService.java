package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import bg.softuni.cozypetshotel.models.dtos.RegisterDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.Booking;
import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.session.AppUserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    public void register(RegisterDTO registerDTO);

    public Optional<AppUserDetails> getCurrentUser();

    public UserDTO findByEmail(String email);

//    public List<BookingDTO> getUserBookings(String userId);

//    public void updateBookings(UserDTO userDTO);

//    public List<Booking> getActiveBookings();

    public void editEmail(String email, String newEmail);

    public void editPassword(String email, String currentPassword, String newPassword);

    public void editUsername(String email, String username);

    public void editLastName(String email, String lastName);

    public void editContactNumber(String email, String contactNumber);
    public UserDTO convertToDTO(User user);

//    public void deleteActiveBooking(Long bookingId, UserDTO userDTO);
//    public void cancelBooking(Long bookingId);
//    List<UserDTO> findAllUsers();
//
}

