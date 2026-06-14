package me.winterbelle.prompvault.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PolicyKeywordDto(
        @NotBlank(message = "Keyword is required") @Size(max = 255, message = "Keyword must be 255 characters or fewer") String text) {
}