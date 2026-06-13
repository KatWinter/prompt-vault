package me.winterbelle.prompvault.repositories;

import me.winterbelle.prompvault.models.data.PromptHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromptHistoryRepository extends JpaRepository<PromptHistoryItem, Long> {
    List<PromptHistoryItem> findByAccountId(Long accountId);

}
