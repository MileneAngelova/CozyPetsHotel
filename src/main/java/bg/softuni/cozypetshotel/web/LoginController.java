package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {
    private final ModelMapper modelMapper;

    public LoginController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFail(@ModelAttribute("email") String email, Model model) {
        model.addAttribute("email", email);
        model.addAttribute("loginError", "Incorrect email or password!");
        UserDTO userDTO = this.modelMapper.map(email, UserDTO.class);

//       if (!userDTO.isActive()) {
//        model.addAttribute("loginError", "Your account was blocked!");
//       }
        return "login";
    }
}
