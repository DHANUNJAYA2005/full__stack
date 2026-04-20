package com.example.user_service;





import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
@Entity
@Table(name = "registrations")
public class Registration {

	@NotBlank(message = "Name is required")
	private String studentName;

	@NotBlank(message = "Email is required")
	@Email(message = "Enter valid email")
	private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}