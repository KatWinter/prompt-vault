package me.winterbelle.prompvault.services.prompthistoryservice;

import me.winterbelle.prompvault.models.dtos.PromptHistoryItemDto;

import java.util.List;

public interface PromptHistoryService {

    List<PromptHistoryItemDto> getHistoryForUser(Long userId);

}