package me.winterbelle.prompvault.services.flaggedprompt;

import me.winterbelle.prompvault.models.data.Prompt;
import me.winterbelle.prompvault.models.dtos.FlaggedPromptDto;

import java.util.List;

public interface FlaggedPromptService {

    List<FlaggedPromptDto> findAll();

    void rescanPrompt(Prompt prompt);

    String getKeywordTextForPrompt(Long promptId);

    void deleteByPrompt(Prompt prompt);

}