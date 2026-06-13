package me.winterbelle.prompvault.repositories;

import me.winterbelle.prompvault.models.data.PromptCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptCategoryRepository extends JpaRepository<PromptCategory, Long> {
}
