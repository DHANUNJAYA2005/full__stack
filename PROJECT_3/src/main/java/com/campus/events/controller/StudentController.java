package com.campus.events.controller;

import com.campus.events.model.Registration;
import com.campus.events.model.Student;
import com.campus.events.service.EventService;

import com.campus.events.service.RegistrationService;
import com.campus.events.service.StudentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @Autowired
    private RegistrationService registrationService;
    
    @Autowired
    private EventService eventService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("student") Student student, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "student/register";
        }
        if (studentService.findByEmail(student.getEmail()).isPresent()) {
            result.rejectValue("email", "error.student", "Email is already registered");
            return "student/register";
        }
        
        student.setVerified(true);
        studentService.saveStudent(student);
        
        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/student/login";
    }



    @GetMapping("/login")
    public String showLoginForm() {
        return "student/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes) {
        Student student = studentService.authenticate(email, password);
        if (student != null) {
            session.setAttribute("loggedStudent", student);
            return "redirect:/student/dashboard";
        }
        redirectAttributes.addFlashAttribute("error", "Invalid email or password");
        return "redirect:/student/login";
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("loggedStudent");
        if (student == null) return "redirect:/student/login";
        
        List<Registration> registrations = registrationService.getRegistrationsByStudent(student.getId());
        model.addAttribute("student", student);
        model.addAttribute("registrationsCount", registrations.size());
        
        return "student/dashboard";
    }

    @GetMapping("/my-events")
    public String showMyEvents(HttpSession session, Model model) {
        Student student = (Student) session.getAttribute("loggedStudent");
        if (student == null) return "redirect:/student/login";
        
        List<Registration> registrations = registrationService.getRegistrationsByStudent(student.getId());
        model.addAttribute("registrations", registrations);
        return "student/my-events";
    }

    @PostMapping("/feedback/{registrationId}")
    public String submitFeedback(@PathVariable Long registrationId, @RequestParam("feedback") String feedback, @RequestParam("rating") int rating, HttpSession session) {
        Student student = (Student) session.getAttribute("loggedStudent");
        if (student == null) return "redirect:/student/login";
        
        registrationService.submitFeedback(registrationId, feedback, rating);
        return "redirect:/student/my-events";
    }
    
    @PostMapping("/register-event/{eventId}")
    public String registerForEvent(@PathVariable Long eventId, HttpSession session, RedirectAttributes redirectAttributes) {
        Student student = (Student) session.getAttribute("loggedStudent");
        if (student == null) {
            return "redirect:/student/login";
        }
        
        try {
            registrationService.registerStudentForEvent(student.getId(), eventId);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully registered for the event!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        return "redirect:/events/detail/" + eventId;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
