package me.winterbelle.prompvault.services.flaggedprompt;

import me.winterbelle.prompvault.models.dtos.FlaggedPromptDto;

import java.util.List;

public interface FlaggedPromptService {

    List<FlaggedPromptDto> findAll();
}