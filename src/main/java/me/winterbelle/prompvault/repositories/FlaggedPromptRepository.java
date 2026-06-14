package me.winterbelle.prompvault.repositories;

import me.winterbelle.prompvault.models.data.FlaggedPrompt;
import me.winterbelle.prompvault.models.data.PolicyKeyword;
import me.winterbelle.prompvault.models.data.Prompt;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlaggedPromptRepository extends JpaRepository<FlaggedPrompt, Long> {
    List<FlaggedPrompt> findAllByKeywordsContaining(PolicyKeyword keyword);

    @EntityGraph(attributePaths = {
            "prompt",
            "prompt.account",
            "prompt.category",
            "keywords"
    })
    List<FlaggedPrompt> findAllByOrderByCreatedAtDesc();

    Optional<FlaggedPrompt> findByPrompt(Prompt prompt);

    Optional<FlaggedPrompt> findByPromptId(Long promptId);

}
