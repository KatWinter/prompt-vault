package me.winterbelle.prompvault.services.prompthistoryservice;

import me.winterbelle.prompvault.models.data.PromptHistoryItem;
import me.winterbelle.prompvault.models.dtos.PromptHistoryItemDto;
import me.winterbelle.prompvault.repositories.PromptHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptHistoryServiceImpl
        implements PromptHistoryService {

    private final PromptHistoryRepository repository;

    public PromptHistoryServiceImpl(PromptHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PromptHistoryItemDto> getHistoryForUser(Long userId) {

        return repository
                .findByAccount_IdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private PromptHistoryItemDto toDto(
            PromptHistoryItem item
    ) {
        PromptHistoryItemDto dto =
                new PromptHistoryItemDto();

        dto.setId(item.getId());
        dto.setPromptText(item.getPromptText());
        dto.setResponseText(item.getResponseText());
        dto.setCreatedAt(item.getCreatedAt());

        return dto;
    }
}