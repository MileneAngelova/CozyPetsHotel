package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.BookingDTO;
import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.services.BookingService;
import bg.softuni.cozypetshotel.services.UserService;
import bg.softuni.cozypetshotel.session.AppUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserPageController {
    private final UserService userService;
    private final BookingService bookingService;
    public UserPageController(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }
//    @ModelAttribute("userModel")
//    public UserDTO initUser() {
//        return new UserDTO();
//    }


    @GetMapping("/account")
    public String userAccount(Model model, Principal principal) {
        UserDTO userDTO = userService.findByEmail(principal.getName());
        model.addAttribute("userData", userDTO);
        this.userService.getCurrentUser();
        return "user-info";
    }

//    @GetMapping("/bookings")
//    public String userBookings(Model model, Principal principal) {
//        UserDTO byEmail = userService.findByEmail(principal.getName());
//
//        if (!model.containsAttribute("myBookings")) {
//            model.addAttribute("myBookings", this.bookingService.getUserBookings(byEmail.getUuid()));
//        return "user-bookings";
//        }





//        UserDTO byEmail = userService.findByEmail(principal.getName());
//        model.addAttribute("userData", byEmail);
//        this.userService.updateBookings(byEmail);

//        return "user-bookings";
//    }

    @GetMapping("/settings")
    public String userSettings(Model model, Principal principal) {
        UserDTO byEmail = userService.findByEmail(principal.getName());
        model.addAttribute("userData", byEmail);
//        this.userService.updateBookings(byEmail);
        return "/user-settings";
    }

    @PostMapping("/update/email")
    public String userSettings(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestParam("email") String email,
                               RedirectAttributes redirectAttributes) {
        UserDTO userDTO = userService.findByEmail(userDetails.getUsername());

        try {
            userService.editEmail(userDTO.getEmail(), email);
            redirectAttributes.addFlashAttribute("message", "The email was successfully updated.");
        return "redirect:/user/settings";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        return "redirect:/user/settings";
        }
    }

    @PostMapping("/update/password")
        public String updatePassword(@AuthenticationPrincipal UserDetails userDetails,
                                     @RequestParam("currentPassword") String currentPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     RedirectAttributes redirectAttributes) {
        UserDTO userDTO = userService.findByEmail(userDetails.getUsername());

        try {
            userService.editPassword(userDTO.getEmail(), currentPassword, newPassword);
            redirectAttributes.addFlashAttribute("message", "The password was successfully updated.");
            return "redirect:/user/settings";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/settings";
        }
    }

    @PostMapping("/update/username")
    public String updateUsername(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam("username") String username,
                                 RedirectAttributes redirectAttributes) {
        UserDTO userDTO = userService.findByEmail(userDetails.getUsername());

        try {
            userService.editUsername(userDTO.getEmail(), username);
            redirectAttributes.addFlashAttribute("message", "The username was successfully updated.");
            return "redirect:/user/settings";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/settings";
        }
    }

    @PostMapping("/update/contact-number")
    public String updateContactNumber(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam("contactNumber") String contactNumber,
                                 RedirectAttributes redirectAttributes) {
        UserDTO userDTO = userService.findByEmail(userDetails.getUsername());

                try {
            userService.editContactNumber(userDTO.getEmail(), contactNumber);
            redirectAttributes.addFlashAttribute("message", "The contact number is successfully updated.");
            return "redirect:/user/settings";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/settings";
        }
    }

    @PostMapping("/update/last-name")
    public String updateLastName(@AuthenticationPrincipal UserDetails userDetails,
                                      @RequestParam("lastName") String lastName,
                                      RedirectAttributes redirectAttributes) {
        UserDTO userDTO = userService.findByEmail(userDetails.getUsername());

        try {
            userService.editLastName(userDTO.getEmail(), lastName);
            redirectAttributes.addFlashAttribute("message", "Your last name is successfully updated.");
            return "redirect:/user/settings";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/settings";
        }
    }

//    @Transactional
//    @DeleteMapping("/user/bookings/delete/{id}")
//    public String deleteById(@PathVariable Long id, Principal principal) {
//        UserDTO byEmail = userService.findByEmail(principal.getName());
//        this.bookingService.cancelBooking(id);
//        this.userService.updateBookings(byEmail);
//        return "redirect:/user/bookings";
////        return ResponseEntity.ok().build();
//    }
}




