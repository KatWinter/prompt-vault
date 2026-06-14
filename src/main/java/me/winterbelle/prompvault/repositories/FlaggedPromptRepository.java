package me.winterbelle.prompvault.repositories;

import me.winterbelle.prompvault.models.data.FlaggedPrompt;
import me.winterbelle.prompvault.models.data.PolicyKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlaggedPromptRepository extends JpaRepository<FlaggedPrompt, Long> {
    List<FlaggedPrompt> findAllByKeywordsContaining(PolicyKeyword keyword);
}
