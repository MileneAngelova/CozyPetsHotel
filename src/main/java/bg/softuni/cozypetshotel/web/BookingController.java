package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.AddBookingDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.Booking;
import bg.softuni.cozypetshotel.services.BookingService;
import bg.softuni.cozypetshotel.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public BookingController(BookingService bookingService, UserService userService,
                             ModelMapper modelMapper) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("bookingModel")
    public AddBookingDTO initBooking() {
        return new AddBookingDTO();
    }

    @GetMapping("/book-now")
    public String makeReservation() {
        return "book-now";
    }

    @PostMapping("/book-now")
    public String makeReservation(@Valid AddBookingDTO bookingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("bookingModel", bookingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bookingModel", bindingResult);
            return "redirect:/book-now";
        }

        this.bookingService.makeBooking(bookingModel);
//        this.userService.getActiveBookings().add(this.modelMapper.map(bookingModel, Booking.class));
        return "redirect:/user/bookings";
    }

    //    @Transactional
//    @DeleteMapping("/bookings/delete/{id}")
//    public String deleteById(@PathVariable("id") Long id, Principal principal) {
//        UserDTO byEmail = userService.findByEmail(principal.getName());
////        this.userService.deleteActiveBooking(id, byEmail);
//        this.bookingService.cancelBooking(id);
//        this.userService.updateBookings(byEmail);
//        return "redirect:/user/bookings";
//    }
}
