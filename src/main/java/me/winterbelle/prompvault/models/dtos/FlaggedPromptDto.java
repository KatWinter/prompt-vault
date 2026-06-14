package me.winterbelle.prompvault.models.dtos;

import java.time.LocalDateTime;

public record FlaggedPromptDto(Long id, String promptTitle, String ownerUsername, String categoryName, String keywords,
                               LocalDateTime createdDate) {

}