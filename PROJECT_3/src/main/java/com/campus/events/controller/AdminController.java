package com.campus.events.controller;

import com.campus.events.model.Event;
import com.campus.events.service.EventService;
import com.campus.events.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventService eventService;
    
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("stats", eventService.getEventStats());
        return "admin/dashboard";
    }

    @GetMapping("/events")
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "admin/events/list";
    }

    @GetMapping("/events/add")
    public String showAddEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "admin/events/add";
    }

    @PostMapping("/events/add")
    public String addEvent(@Valid @ModelAttribute("event") Event event, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/events/add";
        }
        eventService.createEvent(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/events/edit/{id}")
    public String showEditEventForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        return "admin/events/edit";
    }

    @PostMapping("/events/edit/{id}")
    public String editEvent(@PathVariable("id") Long id, @Valid @ModelAttribute("event") Event event, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/events/edit";
        }
        eventService.updateEvent(id, event);
        return "redirect:/admin/events";
    }

    @PostMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/events";
    }

    @GetMapping("/events/stats")
    public String eventStats(Model model) {
        model.addAttribute("stats", eventService.getEventStats());
        // For simplicity, we just pass the stats object
        return "admin/events/stats";
    }

    @GetMapping("/registrations/{eventId}")
    public String listRegistrations(@PathVariable Long eventId, Model model) {
        model.addAttribute("registrations", registrationService.getRegistrationsByEvent(eventId));
        model.addAttribute("event", eventService.getEventById(eventId));
        return "admin/registrations";
    }
}
