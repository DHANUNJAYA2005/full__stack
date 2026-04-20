package com.example.user_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.user_service.Event;
import com.example.user_service.EventService;
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventService service;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("events", service.getAllEvents());
        return "admin-dashboard";
    }

    @GetMapping("/add")
    public String addEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "add-event";
    }

    @PostMapping("/save")
    public String saveEvent(@ModelAttribute Event event) {
        service.saveEvent(event);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        service.deleteEvent(id);
        return "redirect:/admin/dashboard";
    }
    
}
