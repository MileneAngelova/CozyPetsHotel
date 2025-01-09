package bg.softuni.cozypetshotel.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/info")
    public String getInfo() {
        return "info";
    }

    @GetMapping("/gallery")
    public String gallery() {
        return "gallery";
    }

    @GetMapping("/prices")
    public String prices() {
        return "prices";
    }

    @GetMapping("/services")
    public String services() {
        return "services";
    }

    @GetMapping("/book-now")
    public String bookNow() {
        return "book-now";
    }
}
