package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.dtos.AddBookingDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.Booking;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.repositories.BookingRepository;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
public class BookingService {
    private final RestClient bookingsRestClient;
    private Logger LOGGER = LoggerFactory.getLogger(BookingService.class);
    private final UserRepository userRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    public BookingService(@Qualifier("bookingsRestClient") RestClient bookingsRestClient, UserRepository userRepository, UserService userService, BookingRepository bookingRepository, ModelMapper modelMapper) {
        this.bookingsRestClient = bookingsRestClient;
        this.userRepository = userRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
    }

    public void makeBooking(AddBookingDTO addBookingDTO) {
        LOGGER.info("Creating new booking...");

        Optional<User> byEmail = this.userRepository.findByEmail(addBookingDTO.getEmail());
        if (byEmail.isPresent()) {
            RestClient.ResponseSpec retrieve = bookingsRestClient
                    .post()
                    .uri("http://localhost:8081/bookings")
                    .accept(MediaType.APPLICATION_JSON)
                    .body(addBookingDTO)
                    .retrieve();
//        if (byEmail.isPresent()) {
//            Booking newBooking = this.modelMapper.map(bookingDTO, Booking.class);
//            this.bookingRepository.save(newBooking);
//            ResponseEntity<Booking> newBooking = retrieve.toEntity(Booking.class);

//            byEmail.get().getActiveBookings().add(this.modelMapper.map(addBookingDTO, Booking.class));
//            this.userRepository.save(this.modelMapper.map(byEmail, User.class));
            this.userService.updateBookings(this.modelMapper.map(byEmail, UserDTO.class));
        }
    }

    public void cancelBooking(Long id) {
        bookingsRestClient
                .delete()
                .uri("/bookings/delete/{id}")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
//        this.bookingRepository.deleteById(id);
    }

    //    @Transactional
    public Booking getBookingById(Long id) {
        return this.bookingRepository.getReferenceById(id);
    }

    private static Booking mapToBooking(AddBookingDTO addBookingDTO) {
        return new Booking()
                .setId(addBookingDTO.getId())
                .setBreed(addBookingDTO.getBreed())
                .setEmail(addBookingDTO.getEmail())
                .setAdditionalInformation(addBookingDTO.getAdditionalInformation())
                .setCheckIn(addBookingDTO.getCheckIn())
                .setCheckOut(addBookingDTO.getCheckOut())
                .setContactNumber(addBookingDTO.getContactNumber())
                .setFirstName(addBookingDTO.getFirstName())
                .setLastName(addBookingDTO.getLastName())
                .setNumberOfPets(addBookingDTO.getNumberOfPets())
                .setPetName(addBookingDTO.getPetName())
                .setPetType(addBookingDTO.getPetType());
    }
}
