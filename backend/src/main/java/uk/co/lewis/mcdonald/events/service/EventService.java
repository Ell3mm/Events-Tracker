package uk.co.lewis.mcdonald.events.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.lewis.mcdonald.events.domain.Event;
import uk.co.lewis.mcdonald.events.domain.Venue;
import uk.co.lewis.mcdonald.events.dto.EventRequest;
import uk.co.lewis.mcdonald.events.dto.EventResponse;
import uk.co.lewis.mcdonald.events.repository.EventRepository;
import uk.co.lewis.mcdonald.events.repository.VenueRepository;

// THIS SERVICE CREATES AND READS EVENTS IN A SIMPLE BUSINESS LOGIC LAYER.
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAllByOrderByStartDateTimeAsc()
                .stream()
                .map(EventResponse::from)
                .toList();
    }

    @Transactional
    public EventResponse createEvent(EventRequest request) {
        LocalDateTime start = request.startDateTime();
        LocalDateTime end = request.endDateTime();

        if (end.isBefore(start) || end.isEqual(start)) {
            throw new IllegalArgumentException("Event end time must be after the start time.");
        }

        Venue venue = venueRepository.findByNameIgnoreCase(request.venueName())
                .orElseGet(() -> venueRepository.save(new Venue(request.venueName())));

        Event event = new Event(
                request.title(),
                request.description(),
                request.category(),
                venue,
                start,
                end,
                request.price(),
                request.websiteUrl()
        );

        Event savedEvent = eventRepository.save(event);
        return EventResponse.from(savedEvent);
    }
}
