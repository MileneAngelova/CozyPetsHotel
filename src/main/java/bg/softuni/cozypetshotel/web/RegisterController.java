package bg.softuni.cozypetshotel.web;

import bg.softuni.cozypetshotel.models.dtos.RegisterDTO;
import bg.softuni.cozypetshotel.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerModel")
    public RegisterDTO initRegister() {
        return new RegisterDTO();
    }

    @GetMapping("/register")
    public String register() {
          return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterDTO registerModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !registerModel.getPassword().equals(registerModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("registerModel", registerModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerModel", bindingResult);

            return "redirect:/register";
        }
            this.userService.register(registerModel);
            return "redirect:/login";
    }
}
