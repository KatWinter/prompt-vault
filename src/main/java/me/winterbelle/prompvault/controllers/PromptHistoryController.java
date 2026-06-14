package me.winterbelle.prompvault.controllers;

import me.winterbelle.prompvault.services.prompthistoryservice.PromptHistoryService;
import me.winterbelle.prompvault.utils.helpers.auth.UserDetailsObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PromptHistoryController {

    private final PromptHistoryService historyService;

    public PromptHistoryController(
            PromptHistoryService historyService
    ) {
        this.historyService = historyService;
    }

    @GetMapping("/prompts/history")
    public String history(
            @AuthenticationPrincipal UserDetailsObject user,
            Model model
    ) {

        model.addAttribute(
                "historyItems",
                historyService.getHistoryForUser(
                        user.getUserId()
                )
        );

        return "prompts/history";
    }
}