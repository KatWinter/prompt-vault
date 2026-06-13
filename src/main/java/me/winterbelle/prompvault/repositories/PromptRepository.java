package me.winterbelle.prompvault.repositories;

import me.winterbelle.prompvault.models.data.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
}
