package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.dtos.AddBookingDTO;
import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.repositories.BookingRepository;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
    private final RestClient bookingsRestClient;
    private final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);
    private final UserRepository userRepository;

    public BookingService(@Qualifier("bookingsRestClient") RestClient bookingsRestClient, UserRepository userRepository, UserService userService, BookingRepository bookingRepository) {
        this.bookingsRestClient = bookingsRestClient;
        this.userRepository = userRepository;
    }

    public void makeBooking(AddBookingDTO addBookingDTO) {
        LOGGER.info("Creating new booking...");

        Optional<User> byEmail = this.userRepository.findByEmail(addBookingDTO.getEmail());
        if (byEmail.isPresent()) {
            bookingsRestClient
                    .post()
                    .uri("/bookings")
                    .accept(MediaType.APPLICATION_JSON)
                    .body(addBookingDTO)
                    .retrieve();
        }
    }

    public void cancelBooking(Long id) {
        bookingsRestClient
                .delete()
                .uri("/bookings/delete/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public List<BookingDTO> getUserBookings(UUID uuid) {
        LOGGER.info("Getting user bookings...");
        getAuthentication();

        return bookingsRestClient
                .get()
                .uri("/bookings/user", uuid)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public List<BookingDTO> getAllBookings() {
       return bookingsRestClient
                .get()
                .uri("/bookings/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

//        assert bookings != null;

//        return new PageImpl<>(bookings.getContent());
    }

    private void getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Optional<User> user = userRepository.findByEmail(principal.getUsername());
        if (user.isPresent()) {
            if (!user.get().isActive()) {
                throw new RuntimeException("User is disabled");
            }
        }
    }
}
