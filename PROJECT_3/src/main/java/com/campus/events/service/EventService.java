package com.campus.events.service;

import com.campus.events.exception.EventNotFoundException;
import com.campus.events.model.Event;
import com.campus.events.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getAllUpcomingEvents() {
        return eventRepository.findByEventDateGreaterThanEqualOrderByEventDateAsc(LocalDate.now());
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + id));
    }

    public List<Event> searchEvents(String dept, String type, LocalDate from, LocalDate to) {
        // Simplified search logic; in a real app, you'd use Specifications or QueryDSL for dynamic filtering
        if (dept != null && !dept.isEmpty()) {
            return eventRepository.findByDepartmentContainingIgnoreCase(dept);
        } else if (type != null && !type.isEmpty()) {
            return eventRepository.findByEventType(type);
        } else if (from != null && to != null) {
            return eventRepository.findByEventDateBetween(from, to);
        }
        return getAllUpcomingEvents();
    }

    @Transactional
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Transactional
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = getEventById(id);
        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setDepartment(eventDetails.getDepartment());
        event.setEventType(eventDetails.getEventType());
        event.setEventDate(eventDetails.getEventDate());
        event.setTime(eventDetails.getTime());
        event.setVenue(eventDetails.getVenue());
        event.setCapacity(eventDetails.getCapacity());
        event.setImageUrl(eventDetails.getImageUrl());
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(Long id) {
        Event event = getEventById(id);
        eventRepository.delete(event);
    }

    public Map<String, Object> getEventStats() {
        Map<String, Object> stats = new HashMap<>();
        
        List<Object[]> deptStats = eventRepository.countRegistrationsByDepartment();
        stats.put("departmentStats", deptStats);
        
        List<Object[]> typeStats = eventRepository.countRegistrationsByType();
        stats.put("typeStats", typeStats);
        
        return stats;
    }
}
