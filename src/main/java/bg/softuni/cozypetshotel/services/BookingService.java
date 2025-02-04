package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import bg.softuni.cozypetshotel.models.entities.Booking;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.repositories.BookingRepository;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookingService {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    public BookingService(UserRepository userRepository, BookingRepository bookingRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
    }

    public void makeBooking(BookingDTO bookingDTO) {
        Optional<User> byEmail = this.userRepository.findByEmail(bookingDTO.getEmail());
        if (byEmail.isPresent()) {
            Booking newBooking = this.modelMapper.map(bookingDTO, Booking.class);
            this.bookingRepository.save(newBooking);

            byEmail.get().getActiveBookings().add(newBooking);
            this.userRepository.save(this.modelMapper.map(byEmail, User.class));
        }
    }
}
