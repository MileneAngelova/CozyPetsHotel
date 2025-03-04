package bg.softuni.cozypetshotel.services.impl;

import bg.softuni.cozypetshotel.models.dtos.EditUserDetailsDTO;
import bg.softuni.cozypetshotel.models.dtos.RegisterDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.Booking;
import bg.softuni.cozypetshotel.models.entities.Role;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.models.enums.RoleNameEnum;
import bg.softuni.cozypetshotel.repositories.BookingRepository;
import bg.softuni.cozypetshotel.repositories.RoleRepository;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import bg.softuni.cozypetshotel.services.UserService;
import bg.softuni.cozypetshotel.session.AppUserDetails;
import bg.softuni.cozypetshotel.validation.FieldsMatch;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@FieldsMatch(first = "password", second = "confirmPassword", message = "Passwords do not match!")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public UserServiceImpl(UserRepository userRepository, BookingRepository bookingRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        Optional<User> byEmail = this.userRepository.findByEmail(registerDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new IllegalArgumentException("Email " + registerDTO.getEmail() + " is already registered!");
        }

        User newUser = this.modelMapper.map(registerDTO, User.class);
        Role newUserRole = this.roleRepository.findByRole(RoleNameEnum.USER);
        newUser.setPassword(this.passwordEncoder.encode(registerDTO.getPassword()));
        newUser.getRoles().add(newUserRole);
        newUser.setActive(true);

        userRepository.save(newUser);
    }

    @Override
    public Optional<AppUserDetails> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof AppUserDetails appUserDetails) {
            return Optional.of(appUserDetails);
        }
        return Optional.empty();
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " was not found!"));
        return this.modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void updateBookings(UserDTO userDTO) {
        List<Booking> activeBookings = userDTO.getActiveBookings();

        if (activeBookings.size() > 0) {
            activeBookings.forEach(booking -> {
                LocalDate checkOut = booking.getCheckOut();
                if (checkOut.isBefore(LocalDate.now())) {
                    List<Booking> expiredBookings = userDTO.getExpiredBookings();
                    expiredBookings.add(booking);
                    activeBookings.remove(booking);
                }
            });
        } else {
            LOGGER.info("No active bookings found");
        }
        this.userRepository.save(this.modelMapper.map(userDTO, User.class));
    }

    @Override
    public void editEmail(Long id, String newEmail) {
        if (userRepository.findByEmail(newEmail).isPresent()) {
            throw new IllegalArgumentException(("User with email " + newEmail + " already exists!"));
        }
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    @Override
    public void editPassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void editUsername(Long id, String username) {
        if (this.userRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if (this.userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("username is already taken!");
        }
        user.setUsername(username);
        userRepository.save(user);
    }

    @Override
    public void editLastName(Long userId, String newLastName) {
        if (this.userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        user.setLastName(newLastName);
        userRepository.save(user);
    }

    @Override
    public void editContactNumber(Long userId, String newContactNumber) {
        if (this.userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        user.setContactNumber(newContactNumber);
        userRepository.save(user);
    }

    @Override
    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setRoles(user.getRoles());
        userDTO.setActive(user.isActive());
        return userDTO;
    }

//    @Override
//    public void cancelBooking(Long bookingId) {
//        Booking bookingToDelete = bookingRepository.findById(bookingId).orElse(null);
//        assert bookingToDelete != null;
//        User user = bookingToDelete.getUser();
//        List<Booking> activeBookings = user.getActiveBookings();
//        activeBookings.remove(bookingToDelete);
//        this.userRepository.save(user);
//        this.bookingRepository.deleteById(bookingId);
//    }
}

