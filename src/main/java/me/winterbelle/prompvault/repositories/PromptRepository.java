package me.winterbelle.prompvault.repositories;

import me.winterbelle.prompvault.models.data.Prompt;
import me.winterbelle.prompvault.utils.enums.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromptRepository extends JpaRepository<Prompt, Long> {

    List<Prompt> findByAccountId(Long accountId);

    boolean existsByCategoryId(Long categoryId);

    List<Prompt> findByVisibility(Visibility visibility);
}
