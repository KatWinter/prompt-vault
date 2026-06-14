package me.winterbelle.prompvault.controllers;

import me.winterbelle.prompvault.models.data.Prompt;
import me.winterbelle.prompvault.models.data.PromptCategory;
import me.winterbelle.prompvault.models.data.PromptHistoryItem;
import me.winterbelle.prompvault.models.data.PromptVaultUser;
import me.winterbelle.prompvault.models.dtos.PromptDto;
import me.winterbelle.prompvault.services.promptcategories.PromptCategoryService;
import me.winterbelle.prompvault.services.prompts.PromptService;
import me.winterbelle.prompvault.services.user.PromptVaultUserService;
import me.winterbelle.prompvault.utils.enums.Visibility;
import me.winterbelle.prompvault.utils.helpers.auth.UserDetailsObject;
import me.winterbelle.prompvault.utils.helpers.security.InputSanitizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/prompts")
public class PromptController {

    private final PromptService promptService;
    private final PromptCategoryService promptCategoryService;
    private final PromptVaultUserService promptVaultUserService;
    private final InputSanitizer inputSanitizer;

    public PromptController(
            PromptService promptService,
            PromptCategoryService promptCategoryService,
            PromptVaultUserService promptVaultUserService,
            InputSanitizer inputSanitizer) {
        this.promptService = promptService;
        this.promptCategoryService = promptCategoryService;
        this.promptVaultUserService = promptVaultUserService;
        this.inputSanitizer = inputSanitizer;
    }

    @GetMapping
    public String listPrompts(
            Authentication authentication,
            Model model) {

        UserDetailsObject user =
                (UserDetailsObject) authentication.getPrincipal();

        model.addAttribute(
                "prompts",
                promptService.getPromptsForUser(user.getUserId()));

        return "prompts/list";
    }

    @GetMapping("/create")
    public String showCreatePromptForm(Model model) {

        model.addAttribute("prompt", new PromptDto());
        model.addAttribute("categories", promptCategoryService.findAll());
        model.addAttribute("visibilities", Visibility.values());

        return "prompts/create";
    }

    @PostMapping("/create")
    public String createPrompt(
            @ModelAttribute("prompt") PromptDto promptDto,
            @AuthenticationPrincipal UserDetailsObject user,
            RedirectAttributes redirectAttributes) {

        PromptVaultUser account =
                promptVaultUserService.findUser(user.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PromptCategory category =
                promptCategoryService.findById(promptDto.getCategoryId());

        Prompt prompt = new Prompt();
        prompt.setTitle(inputSanitizer.sanitizePlainText(promptDto.getTitle()));
        prompt.setPromptText(inputSanitizer.sanitizePlainText(promptDto.getPromptText()));
        prompt.setCategory(category);
        prompt.setVisibility(promptDto.getVisibility());
        prompt.setAccount(account);

        promptService.createPrompt(prompt);
        promptService.createPrompt(prompt);

        redirectAttributes.addFlashAttribute(
                "message",
                "Prompt created successfully");

        return "redirect:/prompts";
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