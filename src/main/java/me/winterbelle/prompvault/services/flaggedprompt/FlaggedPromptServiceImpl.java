package me.winterbelle.prompvault.services.flaggedprompt;

import me.winterbelle.prompvault.models.data.FlaggedPrompt;
import me.winterbelle.prompvault.models.data.PolicyKeyword;
import me.winterbelle.prompvault.models.dtos.FlaggedPromptDto;
import me.winterbelle.prompvault.repositories.FlaggedPromptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlaggedPromptServiceImpl implements FlaggedPromptService {

    private final FlaggedPromptRepository flaggedPromptRepository;

    public FlaggedPromptServiceImpl(FlaggedPromptRepository flaggedPromptRepository) {
        this.flaggedPromptRepository = flaggedPromptRepository;
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
}