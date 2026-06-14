package me.winterbelle.prompvault.services.promptcategories;

import me.winterbelle.prompvault.models.data.PromptCategory;
import me.winterbelle.prompvault.models.dtos.PromptCategoryDto;
import me.winterbelle.prompvault.repositories.PromptCategoryRepository;
import me.winterbelle.prompvault.repositories.PromptRepository;
import me.winterbelle.prompvault.utils.helpers.security.InputSanitizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PromptCategoryServiceImpl implements PromptCategoryService {

    private final PromptCategoryRepository promptCategoryRepository;
    private final PromptRepository promptRepository;
    private final InputSanitizer sanitizer;

    public PromptCategoryServiceImpl(
            PromptCategoryRepository promptCategoryRepository,
            PromptRepository promptRepository,
            InputSanitizer sanitizer) {

        this.promptCategoryRepository = promptCategoryRepository;
        this.promptRepository = promptRepository;
        this.sanitizer = sanitizer;
    }

    @Override
    public List<PromptCategory> findAll() {
        return promptCategoryRepository.findAll();
    }

    @Override
    public PromptCategory findById(Long id) {
        return promptCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Override
    @Transactional
    public void create(PromptCategoryDto dto) {
        String name = sanitizer.sanitizePlainText(dto.getName());

        if (name.isBlank()) {
            throw new IllegalArgumentException("Category name is required");
        }

        if (promptCategoryRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Category already exists");
        }

        PromptCategory category = new PromptCategory();
        category.setName(name);

        promptCategoryRepository.save(category);
    }

    @Override
    @Transactional
    public void update(Long id, PromptCategoryDto dto) {
        PromptCategory category = findById(id);

        String name = sanitizer.sanitizePlainText(dto.getName());

        if (name.isBlank()) {
            throw new IllegalArgumentException("Category name is required");
        }

        if (promptCategoryRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new IllegalArgumentException("Category already exists");
        }

        category.setName(name);
        promptCategoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (promptRepository.existsByCategoryId(id)) {
            throw new IllegalStateException(
                    "Cannot delete category because it is assigned to one or more prompts"
            );
        }
        promptCategoryRepository.deleteById(id);
    }
}