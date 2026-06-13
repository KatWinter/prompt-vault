package me.winterbelle.prompvault.services.prompts;

import jakarta.persistence.EntityNotFoundException;
import me.winterbelle.prompvault.models.data.Prompt;
import me.winterbelle.prompvault.models.data.PromptHistoryItem;
import me.winterbelle.prompvault.repositories.PromptHistoryRepository;
import me.winterbelle.prompvault.repositories.PromptRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PromptServiceImpl implements PromptService {

    private final PromptRepository promptRepository;
    private final PromptHistoryRepository promptHistoryRepository;

    public PromptServiceImpl(PromptRepository promptRepository, PromptHistoryRepository promptHistoryRepository) {

        this.promptRepository = promptRepository;
        this.promptHistoryRepository = promptHistoryRepository;
    }

    public List<Prompt> getPromptsForUser(Long userId) {
        return promptRepository.findByAccountId(userId);
    }

    @Override
    public List<Prompt> getPrivatePromptsForUser(Long userId) {
        return List.of();
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
        return null;
    }

    @Override
    public void deletePrompt(Long promptId) {
        promptRepository.deleteById(promptId);
    }

    @Override
    public PromptHistoryItem sendPrompt(Long promptId) {
        Prompt prompt = getPrompt(promptId);

        PromptHistoryItem historyItem = new PromptHistoryItem();

        historyItem.setAccount(prompt.getAccount());

        historyItem.setPromptText(prompt.getPromptText());

        historyItem.setRequestTime(LocalDateTime.now());

        historyItem.setResponseText(String.format("AI Service responded with 'TODO' at %s for prompt '%s'", LocalDateTime.now(), prompt.getPromptText()));

        return promptHistoryRepository.save(historyItem);
    }


}