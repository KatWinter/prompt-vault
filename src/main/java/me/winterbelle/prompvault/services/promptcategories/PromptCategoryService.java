package me.winterbelle.prompvault.services.promptcategories;

import me.winterbelle.prompvault.models.data.PromptCategory;
import me.winterbelle.prompvault.models.dtos.PromptCategoryDto;

import java.util.List;

public interface PromptCategoryService {

    List<PromptCategory> findAll();

    PromptCategory findById(Long id);

    void create(PromptCategoryDto dto);

    void update(Long id, PromptCategoryDto dto);

    void delete(Long id);
}