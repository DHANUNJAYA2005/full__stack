package com.campus.events.service;

import com.campus.events.model.Student;
import com.campus.events.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Student saveStudent(Student student) {
        if(student.getPassword() != null && !student.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(student.getPassword()));
        }
        return studentRepository.save(student);
    }

    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public Student authenticate(String email, String rawPassword) {
        Optional<Student> studentOpt = studentRepository.findByEmail(email);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            if (passwordEncoder.matches(rawPassword, student.getPassword())) {
                return student;
            }
        }
        return null;
    }

    @Transactional
    public void markVerified(String email) {
        studentRepository.findByEmail(email).ifPresent(student -> {
            student.setVerified(true);
            studentRepository.save(student);
        });
    }
}
