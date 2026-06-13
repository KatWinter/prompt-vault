package me.winterbelle.prompvault.controllers;

import me.winterbelle.prompvault.models.data.PromptHistoryItem;
import me.winterbelle.prompvault.services.prompts.PromptServiceImpl;
import me.winterbelle.prompvault.utils.helpers.auth.UserDetailsObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/prompts")
public class PromptController {

    private final PromptServiceImpl promptService;

    public PromptController(PromptServiceImpl promptService) {
        this.promptService = promptService;
    }

    @GetMapping
    public String listPrompts(
            @SessionAttribute UserDetailsObject user,
            Model model) {

        model.addAttribute(
                "prompts",
                promptService.getPromptsForUser(
                        user.getUserId()));

        return "prompts/list";
    }

    @PostMapping("/send/{id}")
    public String sendPrompt(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        PromptHistoryItem result =
                promptService.sendPrompt(id);

        redirectAttributes.addFlashAttribute(
                "message",
                result.getResponseText());

        return "redirect:/prompts";
    }
}