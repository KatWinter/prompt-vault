package me.winterbelle.prompvault.controllers.admin;

import jakarta.validation.Valid;
import me.winterbelle.prompvault.models.dtos.PromptCategoryDto;
import me.winterbelle.prompvault.services.promptcategories.PromptCategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPromptCategoryController {

    private final PromptCategoryService service;

    public AdminPromptCategoryController(PromptCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("categories", service.findAll());
        model.addAttribute("newCategory", new PromptCategoryDto());
        return "admin/categories/index";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("newCategory") PromptCategoryDto dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", service.findAll());
            return "admin/categories/index";
        }

        service.create(dto);
        return "redirect:/admin/categories";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        var category = service.findById(id);

        model.addAttribute("categoryId", id);
        model.addAttribute("category", new PromptCategoryDto(category.getName()));

        return "admin/categories/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("category") PromptCategoryDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/categories/edit";
        }

        service.update(id, dto);
        return "redirect:/admin/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes
    ) {
        try {
            service.delete(id);
        } catch (IllegalStateException ignored) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Cannot Delete in use category"
            );
        }
        return "redirect:/admin/categories";
    }
}