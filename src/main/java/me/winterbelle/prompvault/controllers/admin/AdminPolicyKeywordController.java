package me.winterbelle.prompvault.controllers.admin;

import jakarta.validation.Valid;
import me.winterbelle.prompvault.models.dtos.PolicyKeywordDto;
import me.winterbelle.prompvault.services.policykeyword.PolicyKeywordService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/policy-keywords")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPolicyKeywordController {

    private final PolicyKeywordService service;

    public AdminPolicyKeywordController(PolicyKeywordService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("keywords", service.findAll());
        return "admin/policy-keywords/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("keywordId", null);
        model.addAttribute("keyword", new PolicyKeywordDto(""));
        return "admin/policy-keywords/form";
    }

    @PostMapping("/add")
    public String create(
            @Valid @ModelAttribute("keyword") PolicyKeywordDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/policy-keywords/form";
        }

        service.create(dto);
        return "redirect:/admin/policy-keywords";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        var keyword = service.findById(id);

        model.addAttribute("keywordId", id);
        model.addAttribute("keyword", new PolicyKeywordDto(keyword.getText()));

        return "admin/policy-keywords/form";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("keyword") PolicyKeywordDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "form";
        }

        service.update(id, dto);
        return "redirect:/admin/policy-keywords";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/admin/policy-keywords";
    }
}