package uk.co.lewis.mcdonald.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.co.lewis.mcdonald.events.domain.Event;
import uk.co.lewis.mcdonald.events.domain.EventCategory;
import uk.co.lewis.mcdonald.events.domain.Venue;
import uk.co.lewis.mcdonald.events.dto.EventRequest;
import uk.co.lewis.mcdonald.events.dto.EventResponse;
import uk.co.lewis.mcdonald.events.repository.EventRepository;
import uk.co.lewis.mcdonald.events.repository.VenueRepository;
import uk.co.lewis.mcdonald.events.service.EventService;

// THIS TEST CHECKS THE EVENT SERVICE CAN CREATE AND LIST EVENTS.
@ExtendWith(MockitoExtension.class)
class EventControllerIntegrationTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private VenueRepository venueRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void getAllEventsReturnsEmptyListInitially() {
        when(eventRepository.findAllByOrderByStartDateTimeAsc()).thenReturn(List.of());

        List<EventResponse> events = eventService.getAllEvents();

        assertThat(events).isEmpty();
    }

    @Test
    void createEventAddsNewEvent() {
        EventRequest request = new EventRequest(
                "Summer Sessions",
                "Outdoor live music event in the city centre.",
                EventCategory.GIG,
                "Princes Street Gardens",
                LocalDateTime.of(2026, 10, 15, 19, 0),
                LocalDateTime.of(2026, 10, 15, 22, 0),
                BigDecimal.valueOf(25.00),
                "https://example.com/events/summer-sessions"
        );

        when(venueRepository.findByNameIgnoreCase("Princes Street Gardens"))
                .thenReturn(Optional.empty());
        when(venueRepository.save(any(Venue.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EventResponse response = eventService.createEvent(request);

        assertThat(response.title()).isEqualTo("Summer Sessions");
        assertThat(response.category()).isEqualTo(EventCategory.GIG);
        assertThat(response.venueName()).isEqualTo("Princes Street Gardens");
        verify(venueRepository).save(any(Venue.class));
    }
}
