package dev.claudiu_software.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record TaskRequest(
        @NotBlank
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        String title,

        @Size(min=3, max = 500, message = "Description must be greater than 3 characters and less than 500 characters")
        String description,
        Boolean completed
) {
}
