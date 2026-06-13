package me.winterbelle.prompvault.controllers;


import me.winterbelle.prompvault.utils.helpers.auth.UserDetailsObject;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/req/login")
    public String login() {
        return "login";
    }

    @GetMapping("/req/register")
    public String register() {
        return "register";
    }

    @GetMapping("/home")
    public String home(
            Model model,
            Authentication authentication) {

        UserDetailsObject user =
                (UserDetailsObject) authentication.getPrincipal();

        model.addAttribute("userDetailsObject", user);

        return "home";
    }

}
