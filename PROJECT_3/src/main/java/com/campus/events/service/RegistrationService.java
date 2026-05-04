package com.campus.events.service;

import com.campus.events.exception.AlreadyRegisteredException;
import com.campus.events.exception.EventNotFoundException;
import com.campus.events.model.Event;
import com.campus.events.model.Registration;
import com.campus.events.model.Student;
import com.campus.events.repository.EventRepository;
import com.campus.events.repository.RegistrationRepository;
import com.campus.events.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Registration registerStudentForEvent(Long studentId, Long eventId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        if (registrationRepository.existsByStudentAndEvent(student, event)) {
            throw new AlreadyRegisteredException("Student already registered for this event.");
        }

        if (event.getRegisteredCount() >= event.getCapacity()) {
            throw new RuntimeException("Event is full.");
        }

        Registration registration = new Registration();
        registration.setStudent(student);
        registration.setEvent(event);

        event.setRegisteredCount(event.getRegisteredCount() + 1);
        eventRepository.save(event);

        return registrationRepository.save(registration);
    }

    public List<Registration> getRegistrationsByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return registrationRepository.findByStudent(student);
    }

    public List<Registration> getRegistrationsByEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        return registrationRepository.findByEvent(event);
    }

    @Transactional
    public void submitFeedback(Long registrationId, String feedback, int rating) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
        
        registration.setFeedback(feedback);
        registration.setRating(rating);
        registrationRepository.save(registration);
    }
}
