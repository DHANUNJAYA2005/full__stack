package com.example.user_service;





import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    public Event saveEvent(Event event) {
        return repository.save(event);
    }

    public void deleteEvent(Long id) {
        repository.deleteById(id);
    }

    public Event getEventById(Long id) {
        return repository.findById(id).orElse(null);
    }
}