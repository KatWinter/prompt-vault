package me.winterbelle.prompvault.repositories;

import me.winterbelle.prompvault.models.data.PromptHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptHistoryRepository extends JpaRepository<PromptHistoryItem, Long> {
}
