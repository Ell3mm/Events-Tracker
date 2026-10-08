package uk.co.lewis.mcdonald.events.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uk.co.lewis.mcdonald.events.domain.EventCategory;

// THIS RECORD HOLDS THE DATA SENT TO CREATE A NEW EVENT.
public record EventRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull EventCategory category,
        @NotBlank String venueName,
        @NotNull LocalDateTime startDateTime,
        @NotNull LocalDateTime endDateTime,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @NotBlank String websiteUrl
) {
}
