package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import bg.softuni.cozypetshotel.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public LoginController(ModelMapper modelMapper,
                           UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFail(@ModelAttribute("email") String email, Model model) {
        model.addAttribute("email", email);

        String disabledEmail = email + "disabled";

        if (userRepository.findByEmail(disabledEmail).isPresent()) {
            model.addAttribute("disabled", "Your account was blocked!");
        } else {
            model.addAttribute("loginError", "Incorrect email or password!");
        }
        return "login";
    }
}
