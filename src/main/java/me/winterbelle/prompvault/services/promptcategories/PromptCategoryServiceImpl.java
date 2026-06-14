package me.winterbelle.prompvault.services.promptcategories;

import me.winterbelle.prompvault.models.data.PromptCategory;
import me.winterbelle.prompvault.models.dtos.PromptCategoryDto;
import me.winterbelle.prompvault.repositories.PromptCategoryRepository;
import me.winterbelle.prompvault.utils.helpers.security.InputSanitizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PromptCategoryServiceImpl implements PromptCategoryService {

    private final PromptCategoryRepository repository;
    private final InputSanitizer sanitizer;

    public PromptCategoryServiceImpl(
            PromptCategoryRepository repository,
            InputSanitizer sanitizer) {

        this.repository = repository;
        this.sanitizer = sanitizer;
    }

    @Override
    public List<PromptCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public PromptCategory findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Override
    @Transactional
    public void create(PromptCategoryDto dto) {
        String name = sanitizer.sanitizePlainText(dto.getName());

        if (name.isBlank()) {
            throw new IllegalArgumentException("Category name is required");
        }

        if (repository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Category already exists");
        }

        PromptCategory category = new PromptCategory();
        category.setName(name);

        repository.save(category);
    }

    @Override
    @Transactional
    public void update(Long id, PromptCategoryDto dto) {
        PromptCategory category = findById(id);

        String name = sanitizer.sanitizePlainText(dto.getName());

        if (name.isBlank()) {
            throw new IllegalArgumentException("Category name is required");
        }

        if (repository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new IllegalArgumentException("Category already exists");
        }

        category.setName(name);
        repository.save(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}