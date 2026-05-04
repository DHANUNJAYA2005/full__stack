package com.campus.events.repository;

import com.campus.events.model.Event;
import com.campus.events.model.Registration;
import com.campus.events.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByStudent(Student student);

    List<Registration> findByEvent(Event event);

    boolean existsByStudentAndEvent(Student student, Event event);

    long countByEvent(Event event);

    @Query("SELECT AVG(r.rating) FROM Registration r WHERE r.event.id = :eventId AND r.rating IS NOT NULL")
    Double findAverageRatingByEventId(@Param("eventId") Long eventId);
}
