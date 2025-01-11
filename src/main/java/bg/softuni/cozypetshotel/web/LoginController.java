package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.LoginDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {
    @ModelAttribute("loginModel")
    public LoginDTO initLogin() {
        return new LoginDTO();
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {

        if (error != null) {
            String errorMessage = "Invalid email or password!";
            model.addAttribute("loginError", errorMessage);
        }

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", "");
        }
        return "login";
    }
}
