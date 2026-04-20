package com.example.user_service;



import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByDepartment(String department);
    List<Event> findByEventDate(LocalDate date);
    List<Event> findByDepartmentContaining(String department);
}
