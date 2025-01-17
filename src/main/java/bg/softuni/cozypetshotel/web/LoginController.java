package bg.softuni.cozypetshotel.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFail(@ModelAttribute("email") String email, Model model) {
        model.addAttribute("email", email);
        model.addAttribute("loginError", "Incorrect email or password!");
        return "login";
    }
}
