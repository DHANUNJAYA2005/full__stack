package com.campus.events.dto;

public class EventDTO {
    private Long id;
    private String title;
    private String eventDate;
    private String type;
    private String department;
    private String venue;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
}
