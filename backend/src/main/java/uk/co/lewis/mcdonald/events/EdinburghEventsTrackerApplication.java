package uk.co.lewis.mcdonald.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// THIS CLASS STARTS THE SPRING BOOT APP FOR THE EDINBURGH EVENTS TRACKER.
@SpringBootApplication
public class EdinburghEventsTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdinburghEventsTrackerApplication.class, args);
    }
}
