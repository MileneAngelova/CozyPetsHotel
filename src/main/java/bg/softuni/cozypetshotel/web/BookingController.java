package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.AddBookingDTO;
import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.models.entities.Booking;
import bg.softuni.cozypetshotel.services.BookingService;
import bg.softuni.cozypetshotel.services.UserService;
import bg.softuni.cozypetshotel.session.AppUserDetails;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
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
        return "redirect:/bookings/user";
    }

    @GetMapping("/bookings/user")
    public String getUserBookings(Model model, @AuthenticationPrincipal AppUserDetails appUserDetails) {
        List<BookingDTO> userBookings = bookingService.getUserBookings(appUserDetails.getUuid());
        UserDTO byEmail = userService.findByEmail(appUserDetails.getUsername());
        model.addAttribute("myBookings", userBookings);
        model.addAttribute("userData", byEmail);
        return "user-bookings";
    }

    @Transactional
    @DeleteMapping("/bookings/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        this.bookingService.cancelBooking(id);
        return "redirect:/bookings/user";
    }
}

