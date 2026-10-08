package uk.co.lewis.mcdonald.events.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.co.lewis.mcdonald.events.domain.Event;

// THIS REPOSITORY LOADS AND SAVES EVENTS FROM THE DATABASE.
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOrderByStartDateTimeAsc();
}
