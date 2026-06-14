package me.winterbelle.prompvault.controllers;

import me.winterbelle.prompvault.models.data.PromptVaultUser;
import me.winterbelle.prompvault.models.requests.RegisterUserRequest;
import me.winterbelle.prompvault.services.user.PromptVaultUserService;
import me.winterbelle.prompvault.utils.enums.Role;
import me.winterbelle.prompvault.utils.enums.Status;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Optional;

@Controller
public class RegistrationController {

    final private PasswordEncoder passwordEncoder;

    final private PromptVaultUserService promptVaultUserService;

    RegistrationController(PromptVaultUserService promptVaultUserService, PasswordEncoder passwordEncoder) {
        this.promptVaultUserService = promptVaultUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/req/register")
    public String createUser(@ModelAttribute RegisterUserRequest request, RedirectAttributes redirectAttributes) {
        if (!StringUtils.hasText(request.getUsername())
                || !StringUtils.hasText(request.getEmail())
                || !StringUtils.hasText(request.getPassword())) {
            redirectAttributes.addAttribute("error", "Username, email, and password are required.");
            return "redirect:/req/register";
        }

        if (!Objects.equals(request.getPassword(), request.getPasswordConfirmation())) {
            redirectAttributes.addAttribute("error", "Passwords do not match.");
            return "redirect:/req/register";
        }

        Optional<PromptVaultUser> existingAppUser = promptVaultUserService.findUserByEmail(request.getEmail());

        if (existingAppUser.isPresent()) {
            redirectAttributes.addAttribute("error", "User already exists. Please login.");
            return "redirect:/req/register";
        }

        Optional<PromptVaultUser> existingUsername = promptVaultUserService.findUser(request.getUsername());

        if (existingUsername.isPresent()) {
            redirectAttributes.addAttribute("error", "Username already exists. Please choose another.");
            return "redirect:/req/register";
        }

        PromptVaultUser user = new PromptVaultUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Role.user);
        user.setStatus(Status.enabled);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        promptVaultUserService.saveUser(user);

        redirectAttributes.addAttribute("registered", true);
        return "redirect:/req/login";
    }
}
