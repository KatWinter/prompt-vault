package me.winterbelle.prompvault.models.dtos;

import me.winterbelle.prompvault.utils.enums.Visibility;

public record PromptListItemDto(Long id, String title, String promptText, String categoryName, Visibility visibility,
                                String flaggedKeywords) {
}