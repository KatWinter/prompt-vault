package me.winterbelle.prompvault.services.prompts;

import me.winterbelle.prompvault.models.data.Prompt;
import me.winterbelle.prompvault.models.data.PromptHistoryItem;
import me.winterbelle.prompvault.models.dtos.PromptListItemDto;

import java.util.List;

public interface PromptService {

    List<PromptListItemDto> getPromptListItemsForUser(Long userId);

    List<PromptListItemDto> getSharedPrompts();

    Prompt getPrompt(Long promptId);

    Prompt createPrompt(Prompt prompt);

    Prompt updatePrompt(Prompt prompt);

    void deletePrompt(Long promptId);

    PromptHistoryItem sendPrompt(Long promptId);
}
