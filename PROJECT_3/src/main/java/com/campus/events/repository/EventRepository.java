package com.campus.events.repository;

import com.campus.events.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByDepartmentContainingIgnoreCase(String department);

    List<Event> findByEventType(String type);

    List<Event> findByEventDateBetween(LocalDate start, LocalDate end);

    List<Event> findByEventDateGreaterThanEqualOrderByEventDateAsc(LocalDate date);

    @Query("SELECT e.department, COUNT(r) FROM Registration r JOIN r.event e GROUP BY e.department")
    List<Object[]> countRegistrationsByDepartment();

    @Query("SELECT e.eventType, COUNT(r) FROM Registration r JOIN r.event e GROUP BY e.eventType")
    List<Object[]> countRegistrationsByType();
}
