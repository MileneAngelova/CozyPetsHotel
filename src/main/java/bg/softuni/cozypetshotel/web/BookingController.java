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
        return "redirect:/bookings/user";
    }

    @GetMapping("/bookings/user")
    public String getUserBookings(Model model, Principal principal) {
        List<BookingDTO> userBookings = bookingService.getUserBookings(principal.getName());
        UserDTO byEmail = userService.findByEmail(principal.getName());
        model.addAttribute("myBookings", userBookings);
        model.addAttribute("userData", byEmail);
        return "user-bookings";
    }


//    @GetMapping("/bookings/user/{userId}")
//    public String userBookings(Model model, @AuthenticationPrincipal UserDetails userDetails, @PathVariable ("userId") UUID userId) {
//        String email = userDetails.getUsername();
//        UserDTO user = userService.findByEmail(email);
//
//        if (!model.containsAttribute("myBookings")) {
//            model.addAttribute("myBookings", this.bookingService.getUserBookings(userId));
//            model.addAttribute("userData", user);
//            return "user-bookings";
//        }
//        return "user-bookings";
//    }



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

