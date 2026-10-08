package uk.co.lewis.mcdonald.events.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.co.lewis.mcdonald.events.domain.Venue;

// THIS REPOSITORY LOADS AND SAVES VENUES FROM THE DATABASE.
public interface VenueRepository extends JpaRepository<Venue, Long> {
    Optional<Venue> findByNameIgnoreCase(String name);
}
