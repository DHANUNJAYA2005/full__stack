package com.campus.events.controller;

import com.campus.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String home() {
        return "redirect:/events";
    }

    @GetMapping("/events")
    public String events(Model model) {
        model.addAttribute("events", eventService.getAllUpcomingEvents());
        return "events/list";
    }

    @GetMapping("/events/detail/{id}")
    public String eventDetail(@org.springframework.web.bind.annotation.PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEventById(id));
        return "events/detail";
    }
}
