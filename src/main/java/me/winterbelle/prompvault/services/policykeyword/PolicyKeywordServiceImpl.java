package me.winterbelle.prompvault.services.policykeyword;

import jakarta.transaction.Transactional;
import me.winterbelle.prompvault.models.data.FlaggedPrompt;
import me.winterbelle.prompvault.models.data.PolicyKeyword;
import me.winterbelle.prompvault.models.dtos.PolicyKeywordDto;
import me.winterbelle.prompvault.repositories.FlaggedPromptRepository;
import me.winterbelle.prompvault.repositories.PolicyKeywordRepository;
import me.winterbelle.prompvault.utils.helpers.security.InputSanitizer;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyKeywordServiceImpl implements PolicyKeywordService {

    private final PolicyKeywordRepository policyKeywordRepository;
    private final FlaggedPromptRepository flaggedPromptRepository;
    private final InputSanitizer inputSanitizer;

    public PolicyKeywordServiceImpl(
            PolicyKeywordRepository policyKeywordRepository,
            FlaggedPromptRepository flaggedPromptRepository,
            InputSanitizer inputSanitizer
    ) {
        this.policyKeywordRepository = policyKeywordRepository;
        this.flaggedPromptRepository = flaggedPromptRepository;
        this.inputSanitizer = inputSanitizer;
    }

    @Override
    public List<PolicyKeyword> findAll() {
        return policyKeywordRepository.findAll(Sort.by("text"));
    }

    @Override
    public PolicyKeyword findById(Long id) {
        return policyKeywordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Policy keyword not found"));
    }

    @Override
    public PolicyKeyword create(PolicyKeywordDto dto) {
        String keywordText = sanitizeKeyword(dto.text());

        if (policyKeywordRepository.existsByTextIgnoreCase(keywordText)) {
            throw new IllegalArgumentException("Policy keyword already exists");
        }

        PolicyKeyword keyword = new PolicyKeyword();
        keyword.setText(keywordText);

        return policyKeywordRepository.save(keyword);
    }

    @Override
    public PolicyKeyword update(Long id, PolicyKeywordDto dto) {
        PolicyKeyword keyword = findById(id);

        String keywordText = sanitizeKeyword(dto.text());

        if (policyKeywordRepository.existsByTextIgnoreCaseAndIdNot(keywordText, id)) {
            throw new IllegalArgumentException("Policy keyword already exists");
        }

        keyword.setText(keywordText);

        return policyKeywordRepository.save(keyword);
    }

    private String sanitizeKeyword(String text) {
        String cleaned = inputSanitizer.sanitizePlainText(text);

        if (cleaned == null || cleaned.isBlank()) {
            throw new IllegalArgumentException("Keyword cannot be blank");
        }

        return cleaned.trim();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        PolicyKeyword keyword = findById(id);

        List<FlaggedPrompt> flaggedPrompts =
                flaggedPromptRepository.findAllByKeywordsContaining(keyword);

        for (FlaggedPrompt flaggedPrompt : flaggedPrompts) {
            flaggedPrompt.getKeywords().remove(keyword);

            if (flaggedPrompt.getKeywords().isEmpty()) {
                flaggedPromptRepository.delete(flaggedPrompt);
            } else {
                flaggedPromptRepository.save(flaggedPrompt);
            }
        }

        policyKeywordRepository.delete(keyword);
    }
}