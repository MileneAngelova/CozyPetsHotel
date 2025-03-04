package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.Booking;
import bg.softuni.cozypetshotel.models.entities.User;
import bg.softuni.cozypetshotel.services.BookingService;
import bg.softuni.cozypetshotel.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

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
    public BookingDTO initBooking() {
        return new BookingDTO();
    }

    @GetMapping("/book-now")
    public String makeReservation() {
        return "book-now";
    }

    @PostMapping("/book-now")
    public String makeReservation(@Valid BookingDTO bookingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("bookingModel", bookingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bookingModel", bindingResult);
            return "redirect:/book-now";
        }

        this.bookingService.makeBooking(bookingModel);
        return "redirect:/user/bookings";
    }

    @Transactional
    @DeleteMapping("/user/bookings/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, Principal principal) {
        UserDTO byEmail = userService.findByEmail(principal.getName());
        Booking bookingById = this.bookingService.getBookingById(id);
        List<Booking> activeBookings = byEmail.getActiveBookings();
        activeBookings.remove(bookingById);
//        byEmail.getActiveBookings().remove(bookingById);
        this.bookingService.cancelBooking(bookingById.getId());
        this.userService.updateBookings(byEmail);
        return "redirect:/user/bookings";
//        return ResponseEntity.ok().build();
    }
}
