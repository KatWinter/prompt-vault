package me.winterbelle.prompvault.services.flaggedprompt;

import jakarta.transaction.Transactional;
import me.winterbelle.prompvault.models.data.FlaggedPrompt;
import me.winterbelle.prompvault.models.data.PolicyKeyword;
import me.winterbelle.prompvault.models.data.Prompt;
import me.winterbelle.prompvault.models.dtos.FlaggedPromptDto;
import me.winterbelle.prompvault.repositories.FlaggedPromptRepository;
import me.winterbelle.prompvault.repositories.PolicyKeywordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlaggedPromptServiceImpl implements FlaggedPromptService {

    private final FlaggedPromptRepository flaggedPromptRepository;
    private final PolicyKeywordRepository policyKeywordRepository;

    public FlaggedPromptServiceImpl(FlaggedPromptRepository flaggedPromptRepository, PolicyKeywordRepository policyKeywordRepository
    ) {
        this.flaggedPromptRepository = flaggedPromptRepository;
        this.policyKeywordRepository = policyKeywordRepository;
    }

    @Override
    public List<FlaggedPromptDto> findAll() {
        return flaggedPromptRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private FlaggedPromptDto toDto(FlaggedPrompt flaggedPrompt) {

        String keywords = flaggedPrompt.getKeywords()
                .stream()
                .map(PolicyKeyword::getText)
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));

        return new FlaggedPromptDto(
                flaggedPrompt.getId(),
                flaggedPrompt.getPrompt().getTitle(),
                flaggedPrompt.getPrompt().getAccount().getUsername(),
                flaggedPrompt.getPrompt().getCategory().getName(),
                keywords,
                flaggedPrompt.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public void rescanPrompt(Prompt prompt) {

        String content =
                (prompt.getTitle() + " " + prompt.getPromptText())
                        .toLowerCase();

        List<PolicyKeyword> matchedKeywords =
                policyKeywordRepository.findAll()
                        .stream()
                        .filter(keyword ->
                                content.contains(
                                        keyword.getText().toLowerCase()))
                        .toList();

        FlaggedPrompt existing =
                flaggedPromptRepository.findByPrompt(prompt)
                        .orElse(null);

        if (matchedKeywords.isEmpty()) {

            if (existing != null) {
                flaggedPromptRepository.delete(existing);
            }

            return;
        }

        if (existing == null) {
            existing = new FlaggedPrompt();
            existing.setPrompt(prompt);
        }

        existing.setKeywords(matchedKeywords);

        flaggedPromptRepository.save(existing);
    }

    @Override
    public String getKeywordTextForPrompt(Long promptId) {

        return flaggedPromptRepository.findByPromptId(promptId)
                .map(flaggedPrompt ->
                        flaggedPrompt.getKeywords()
                                .stream()
                                .map(PolicyKeyword::getText)
                                .sorted()
                                .collect(Collectors.joining(", ")))
                .orElse("");
    }

    @Override
    @Transactional
    public void deleteByPrompt(Prompt prompt) {
        flaggedPromptRepository.findByPrompt(prompt)
                .ifPresent(flaggedPromptRepository::delete);
    }
}