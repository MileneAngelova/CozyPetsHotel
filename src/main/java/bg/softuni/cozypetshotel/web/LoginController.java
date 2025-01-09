package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.LoginDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {
    @ModelAttribute("loginModel")
    public LoginDTO initLogin() {
        return new LoginDTO();
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
//
//    @PostMapping("/login")
//    public String login(@Valid LoginDTO loginModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("loginModel", loginModel);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginModel", bindingResult);
//            redirectAttributes.addFlashAttribute("login-error", true);
//            return "redirect:/users/login";
//        }
//        return "redirect:/";
//    }
}
