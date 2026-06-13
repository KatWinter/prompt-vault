package me.winterbelle.prompvault.services.prompts;

import me.winterbelle.prompvault.models.data.Prompt;
import me.winterbelle.prompvault.models.data.PromptHistoryItem;

import java.util.List;

public interface PromptService {
    List<Prompt> getPromptsForUser(Long userId);

    List<Prompt> getPrivatePromptsForUser(Long userId);

    Prompt getPrompt(Long promptId);

    Prompt createPrompt(Prompt prompt);

    Prompt updatePrompt(Prompt prompt);

    void deletePrompt(Long promptId);

    PromptHistoryItem sendPrompt(Long promptId);
}
