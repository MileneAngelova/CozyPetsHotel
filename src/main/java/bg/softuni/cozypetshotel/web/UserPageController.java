package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}




