package bg.softuni.cozypetshotel.services;

import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import bg.softuni.cozypetshotel.repositories.BookingRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void newBooking(BookingDTO bookingDTO) {

    }

}
