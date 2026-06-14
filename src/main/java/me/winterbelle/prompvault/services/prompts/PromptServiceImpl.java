package me.winterbelle.prompvault.services.prompts;

import jakarta.persistence.EntityNotFoundException;
import me.winterbelle.prompvault.models.data.Prompt;
import me.winterbelle.prompvault.models.data.PromptHistoryItem;
import me.winterbelle.prompvault.models.data.PromptVaultUser;
import me.winterbelle.prompvault.models.dtos.PromptListItemDto;
import me.winterbelle.prompvault.repositories.PromptHistoryRepository;
import me.winterbelle.prompvault.repositories.PromptRepository;
import me.winterbelle.prompvault.services.flaggedprompt.FlaggedPromptService;
import me.winterbelle.prompvault.services.user.PromptVaultUserService;
import me.winterbelle.prompvault.utils.enums.Visibility;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PromptServiceImpl implements PromptService {

    private final PromptRepository promptRepository;
    private final PromptHistoryRepository promptHistoryRepository;
    private final FlaggedPromptService flaggedPromptService;
    private final PromptVaultUserService promptVaultUserService;

    public PromptServiceImpl(PromptRepository promptRepository, PromptHistoryRepository promptHistoryRepository, FlaggedPromptService flaggedPromptService, PromptVaultUserService promptVaultUserService) {

        this.promptRepository = promptRepository;
        this.promptHistoryRepository = promptHistoryRepository;
        this.flaggedPromptService = flaggedPromptService;
        this.promptVaultUserService = promptVaultUserService;
    }

    @Override
    public Prompt getPrompt(Long promptId) {
        return promptRepository.findById(promptId).orElseThrow(() -> new EntityNotFoundException("Prompt not found: " + promptId));
    }

    @Override
    public Prompt createPrompt(Prompt prompt) {
        return promptRepository.save(prompt);
    }

    @Override
    public Prompt updatePrompt(Prompt prompt) {
        return promptRepository.save(prompt);
    }

    @Override
    public void deletePrompt(Long promptId) {
        Prompt prompt = getPrompt(promptId);

        flaggedPromptService.deleteByPrompt(prompt);

        promptRepository.delete(prompt);
    }

    @Override
    public PromptHistoryItem sendPrompt(
            Long promptId,
            Long currentUserId
    ) {
        Prompt prompt = getPrompt(promptId);

        PromptVaultUser currentUser =
                promptVaultUserService.findUser(currentUserId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PromptHistoryItem historyItem = new PromptHistoryItem();

        historyItem.setAccount(currentUser);

        historyItem.setPromptText(prompt.getPromptText());

        historyItem.setResponseText(
                String.format(
                        "AI Service responded with 'TODO' at %s for prompt '%s'",
                        LocalDateTime.now(),
                        prompt.getPromptText()
                )
        );

        return promptHistoryRepository.save(historyItem);
    }


    @Override
    public List<PromptListItemDto> getPromptListItemsForUser(Long userId) {

        return promptRepository.findByAccountId(userId)
                .stream()
                .map(prompt -> new PromptListItemDto(
                        prompt.getId(),
                        prompt.getTitle(),
                        prompt.getPromptText(),
                        prompt.getCategory() != null
                                ? prompt.getCategory().getName()
                                : "No Category",
                        prompt.getVisibility(),
                        flaggedPromptService.getKeywordTextForPrompt(
                                prompt.getId())
                ))
                .toList();
    }

    @Override
    public List<PromptListItemDto> getSharedPrompts() {

        return promptRepository.findByVisibility(
                        Visibility.shared)
                .stream()
                .map(prompt -> new PromptListItemDto(
                        prompt.getId(),
                        prompt.getTitle(),
                        prompt.getPromptText(),
                        prompt.getCategory() != null
                                ? prompt.getCategory().getName()
                                : "No Category",
                        prompt.getVisibility(),
                        flaggedPromptService.getKeywordTextForPrompt(
                                prompt.getId())
                ))
                .toList();
    }

}