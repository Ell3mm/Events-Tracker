package uk.co.lewis.mcdonald.events.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import uk.co.lewis.mcdonald.events.domain.Event;
import uk.co.lewis.mcdonald.events.domain.EventCategory;

// THIS RECORD IS THE DATA SENT BACK TO THE FRONTEND AFTER A READ OR CREATE.
public record EventResponse(
        Long id,
        String title,
        String description,
        EventCategory category,
        String venueName,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        BigDecimal price,
        String websiteUrl
) {
    public static EventResponse from(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getCategory(),
                event.getVenue().getName(),
                event.getStartDateTime(),
                event.getEndDateTime(),
                event.getPrice(),
                event.getWebsiteUrl()
        );
    }
}
