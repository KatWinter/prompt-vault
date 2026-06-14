package me.winterbelle.prompvault.controllers.admin;

import me.winterbelle.prompvault.services.flaggedprompt.FlaggedPromptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/flagged-prompts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminFlaggedPromptController {

    private final FlaggedPromptService service;

    public AdminFlaggedPromptController(FlaggedPromptService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("flaggedPrompts", service.findAll());
        return "admin/flagged-prompts/index";
    }
}