package me.winterbelle.prompvault.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.winterbelle.prompvault.utils.enums.Visibility;

public class PromptDto {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Prompt text is required")
    private String promptText;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "Visibility is required")
    private Visibility visibility;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
