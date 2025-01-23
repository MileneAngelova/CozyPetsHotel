package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BookingController {
    @ModelAttribute("booking")
    public BookingDTO initBooking() {
        return new BookingDTO();
    }

    @GetMapping("/book-now")
    public String makeReservation() {
        return "book-now";
    }
}
