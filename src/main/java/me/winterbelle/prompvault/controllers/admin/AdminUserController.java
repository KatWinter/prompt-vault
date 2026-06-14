package me.winterbelle.prompvault.controllers.admin;

import me.winterbelle.prompvault.services.user.PromptVaultUserService;
import me.winterbelle.prompvault.utils.enums.Status;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final PromptVaultUserService userService;

    public AdminUserController(PromptVaultUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/manage-users";
    }

    @PostMapping("/{id}/enable")
    public String enableUser(@PathVariable Long id) {
        userService.updateUserStatus(id, Status.enabled);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/disable")
    public String disableUser(@PathVariable Long id) {
        userService.updateUserStatus(id, Status.disabled);
        return "redirect:/admin/users";
    }
}