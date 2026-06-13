package me.winterbelle.prompvault.repositories;

import me.winterbelle.prompvault.models.data.FlaggedPrompt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlaggedPromptRepository extends JpaRepository<FlaggedPrompt, Long> {
}
