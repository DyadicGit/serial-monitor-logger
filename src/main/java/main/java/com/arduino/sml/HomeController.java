package main.java.com.arduino.sml;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    String index(Model model) {
        model.addAttribute("msg", "Home Page");
        return "home";
    }

    //return "redirect: /bookList";
}