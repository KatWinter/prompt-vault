package me.winterbelle.prompvault.controllers;

import jakarta.validation.Valid;
import me.winterbelle.prompvault.models.data.Prompt;
import me.winterbelle.prompvault.models.data.PromptCategory;
import me.winterbelle.prompvault.models.data.PromptHistoryItem;
import me.winterbelle.prompvault.models.data.PromptVaultUser;
import me.winterbelle.prompvault.models.dtos.PromptDto;
import me.winterbelle.prompvault.services.flaggedprompt.FlaggedPromptService;
import me.winterbelle.prompvault.services.promptcategories.PromptCategoryService;
import me.winterbelle.prompvault.services.prompts.PromptService;
import me.winterbelle.prompvault.services.user.PromptVaultUserService;
import me.winterbelle.prompvault.utils.enums.Visibility;
import me.winterbelle.prompvault.utils.helpers.auth.UserDetailsObject;
import me.winterbelle.prompvault.utils.helpers.security.InputSanitizer;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/prompts")
public class PromptController {

    private final PromptService promptService;
    private final PromptCategoryService promptCategoryService;
    private final PromptVaultUserService promptVaultUserService;
    private final FlaggedPromptService flaggedPromptService;
    private final InputSanitizer inputSanitizer;

    public PromptController(
            PromptService promptService,
            PromptCategoryService promptCategoryService,
            PromptVaultUserService promptVaultUserService,
            FlaggedPromptService flaggedPromptService,
            InputSanitizer inputSanitizer) {
        this.promptService = promptService;
        this.promptCategoryService = promptCategoryService;
        this.promptVaultUserService = promptVaultUserService;
        this.flaggedPromptService = flaggedPromptService;
        this.inputSanitizer = inputSanitizer;
    }

    @GetMapping
    public String listPrompts(
            @AuthenticationPrincipal UserDetailsObject user,
            Model model) {

        model.addAttribute(
                "prompts",
                promptService.getPromptListItemsForUser(
                        user.getUserId()));

        model.addAttribute(
                "isSharedView",
                false);

        return "prompts/list";
    }

    @GetMapping("/shared")
    public String listSharedPrompts(Model model) {

        model.addAttribute(
                "prompts",
                promptService.getSharedPrompts());

        model.addAttribute(
                "isSharedView",
                true);

        return "prompts/list";
    }

    @GetMapping("/create")
    public String showCreatePromptForm(Model model) {

        model.addAttribute("prompt", new PromptDto());
        model.addAttribute("categories", promptCategoryService.findAll());
        model.addAttribute("visibilities", Visibility.values());

        model.addAttribute("formAction", "/prompts/create");
        model.addAttribute("pageTitle", "Create Prompt");
        model.addAttribute("buttonText", "Create Prompt");

        return "prompts/create";
    }

    @PostMapping("/create")
    public String createPrompt(
            @Valid @ModelAttribute("prompt") PromptDto promptDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsObject user,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("categories",
                    promptCategoryService.findAll());

            model.addAttribute("visibilities",
                    Visibility.values());

            model.addAttribute("pageTitle",
                    "Create Prompt");

            model.addAttribute("buttonText",
                    "Create Prompt");

            model.addAttribute("formAction",
                    "/prompts/create");

            return "prompts/create";
        }


        String title =
                inputSanitizer.sanitizePlainText(promptDto.getTitle());
        String promptText =
                inputSanitizer.sanitizePlainText(promptDto.getPromptText());
        if (title.isBlank() || promptText.isBlank()) {

            bindingResult.reject(
                    "prompt.invalid",
                    "Title and prompt text cannot be empty"
            );
            return "prompts/create";
        }
        PromptVaultUser account =
                promptVaultUserService.findUser(user.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PromptCategory category =
                promptCategoryService.findById(promptDto.getCategoryId());

        Prompt prompt = new Prompt();
        prompt.setTitle(title);
        prompt.setPromptText(promptText);
        prompt.setCategory(category);
        prompt.setVisibility(promptDto.getVisibility());
        prompt.setAccount(account);

        prompt = promptService.createPrompt(prompt);
        flaggedPromptService.rescanPrompt(prompt);

        redirectAttributes.addFlashAttribute(
                "message",
                "Prompt created successfully");

        return "redirect:/prompts";
    }

    @GetMapping("/edit/{id}")
    public String showEditPrompt(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsObject user,
            Model model) {

        Prompt prompt = promptService.getPrompt(id);

        if (!prompt.getAccount().getId().equals(user.getUserId())) {
            return "redirect:/prompts";
        }

        PromptDto dto = new PromptDto();
        dto.setTitle(prompt.getTitle());
        dto.setPromptText(prompt.getPromptText());
        dto.setCategoryId(prompt.getCategory().getId());
        dto.setVisibility(prompt.getVisibility());

        model.addAttribute("prompt", dto);
        model.addAttribute("categories", promptCategoryService.findAll());
        model.addAttribute("visibilities", Visibility.values());

        model.addAttribute("formAction", "/prompts/edit/" + id);
        model.addAttribute("pageTitle", "Edit Prompt");
        model.addAttribute("buttonText", "Save Changes");

        return "prompts/create";
    }

    @PostMapping("/edit/{id}")
    public String updatePrompt(
            @PathVariable Long id,
            @Valid @ModelAttribute("prompt") PromptDto dto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsObject user,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("categories",
                    promptCategoryService.findAll());

            model.addAttribute("visibilities",
                    Visibility.values());

            model.addAttribute("pageTitle",
                    "Edit Prompt");

            model.addAttribute("buttonText",
                    "Save Changes");

            model.addAttribute("formAction",
                    "/prompts/edit/" + id);

            return "prompts/create";
        }

        String title =
                inputSanitizer.sanitizePlainText(dto.getTitle());
        String promptText =
                inputSanitizer.sanitizePlainText(dto.getPromptText());

        if (title.isBlank() || promptText.isBlank()) {

            bindingResult.reject(
                    "prompt.invalid",
                    "Title and prompt text cannot be empty"
            );
            return "prompts/create";
        }

        Prompt prompt = promptService.getPrompt(id);

        if (!prompt.getAccount().getId().equals(user.getUserId())) {
            return "redirect:/prompts";
        }

        PromptCategory category =
                promptCategoryService.findById(dto.getCategoryId());

        prompt.setTitle(
                inputSanitizer.sanitizePlainText(dto.getTitle()));

        prompt.setPromptText(
                inputSanitizer.sanitizePlainText(dto.getPromptText()));

        prompt.setCategory(category);
        prompt.setVisibility(dto.getVisibility());

        prompt = promptService.updatePrompt(prompt);
        flaggedPromptService.rescanPrompt(prompt);

        redirectAttributes.addFlashAttribute(
                "message",
                "Prompt updated successfully");

        return "redirect:/prompts";
    }

    @PostMapping("/delete/{id}")
    public String deletePrompt(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsObject user,
            RedirectAttributes redirectAttributes) {

        Prompt prompt = promptService.getPrompt(id);

        if (!prompt.getAccount().getId().equals(user.getUserId())) {
            return "redirect:/prompts";
        }

        promptService.deletePrompt(id);

        redirectAttributes.addFlashAttribute(
                "message",
                "Prompt deleted successfully");

        return "redirect:/prompts";
    }

    @PostMapping("/send/{id}")
    public String sendPrompt(
            @PathVariable Long id,
            @RequestParam(defaultValue = "mine") String returnTo,
            @AuthenticationPrincipal UserDetailsObject user,
            RedirectAttributes redirectAttributes
    ) {

        PromptHistoryItem result =
                promptService.sendPrompt(
                        id,
                        user.getUserId()
                );

        redirectAttributes.addFlashAttribute(
                "message",
                result.getResponseText()
        );

        if ("shared".equals(returnTo)) {
            return "redirect:/prompts/shared";
        }

        return "redirect:/prompts";
    }
}