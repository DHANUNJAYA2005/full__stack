package com.example.user_service;



import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import com.example.user_service.Event;
import com.example.user_service.Registration;
import com.example.user_service.EventRepository;
import com.example.user_service.RegistrationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    // ===============================
    // View All Events (Student Page)
    // ===============================
    @GetMapping("/events")
    public String viewEvents(Model model) {
        model.addAttribute("events", eventRepository.findAll());
        return "events";  // events.html
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/events";
    }
    // ===============================
    // Open Registration Page
    // ===============================
    @GetMapping("/register/{id}")
    public String showRegistrationForm(@PathVariable Long id, Model model) {

        Event event = eventRepository.findById(id).orElse(null);

        Registration registration = new Registration();
        registration.setEvent(event);

        model.addAttribute("registration", registration);

        return "register";  // register.html
    }

    // ===============================
    // Save Registration
    // ===============================
    @PostMapping("/register/save")
    public String saveRegistration(@Valid @ModelAttribute Registration registration,
                                   BindingResult result,
                                   Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        registrationRepository.save(registration);

        model.addAttribute("studentName", registration.getStudentName());

        return "success";
    }
}