package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserPageController {
    private final UserService userService;
    public UserPageController(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/bookings")
    public String userBookings(Model model, Principal principal) {
        UserDTO byEmail = userService.findByEmail(principal.getName());
        model.addAttribute("userData", byEmail);
        this.userService.updateBookings(byEmail);

        return "user-bookings";
    }

    @GetMapping("/settings")
    public String userSettings(Model model, Principal principal) {
        UserDTO byEmail = userService.findByEmail(principal.getName());
        model.addAttribute("userData", byEmail);
        this.userService.updateBookings(byEmail);
        return "/user-settings";
    }

    @PostMapping("/update/email")
    public String userSettings(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestParam("email") String email,
                               RedirectAttributes redirectAttributes) {
        UserDTO userDTO = userService.findByEmail(userDetails.getUsername());

        try {
            userService.editEmail(userDTO.getId(), email);
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
            userService.editPassword(userDTO.getId(), currentPassword, newPassword);
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
            userService.editUsername(userDTO.getId(), username);
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
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        UserDTO userDTO = userService.findByEmail(userDetails.getUsername());
if (bindingResult.hasErrors()) {
    redirectAttributes.addFlashAttribute("userDTO", userDTO);
    redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDTO", bindingResult);
}

        //        try {
            userService.editContactNumber(userDTO.getId(), contactNumber);
//            redirectAttributes.addFlashAttribute("message", "The contact number is successfully updated.");
            return "redirect:/user/settings";
//        } catch (IllegalArgumentException e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/user/settings";
//        }
    }

    @PostMapping("/update/last-name")
    public String updateLastName(@AuthenticationPrincipal UserDetails userDetails,
                                      @RequestParam("lastName") String lastName,
                                      RedirectAttributes redirectAttributes) {
        UserDTO userDTO = userService.findByEmail(userDetails.getUsername());

        try {
            userService.editLastName(userDTO.getId(), lastName);
            redirectAttributes.addFlashAttribute("message", "Your last name is successfully updated.");
            return "redirect:/user/settings";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/settings";
        }
    }
}




