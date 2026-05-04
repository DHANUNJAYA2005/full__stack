package com.campus.events.service;

import com.campus.events.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    @Autowired
    private EventService eventService;

    public String processMessage(String message) {
        String lowerMsg = message.toLowerCase();

        if (lowerMsg.contains("event")) {
            List<Event> events = eventService.getAllUpcomingEvents();
            if (events.isEmpty()) {
                return "There are currently no upcoming events.";
            }
            String titles = events.stream()
                    .limit(5)
                    .map(Event::getTitle)
                    .collect(Collectors.joining(", "));
            return "Here are some upcoming events: " + titles + ". Check the events page for more details!";
        } else if (lowerMsg.contains("register")) {
            return "To register for an event, go to the Events page, click on the event you are interested in, and hit the Register button. Make sure you are logged in!";
        } else if (lowerMsg.contains("help")) {
            return "I can help you find events, give registration instructions, or provide contact info. Just ask!";
        } else if (lowerMsg.contains("contact")) {
            return "You can contact the college administration at events@campus.edu or call 555-1234.";
        } else {
            return "I'm here to help! Ask about events, registration, or schedules.";
        }
    }
}
