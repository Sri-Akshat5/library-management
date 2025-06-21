package com.application.book.library_management.repository;
import com.application.book.library_management.models.StudentEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long > {
    StudentEntity findByEmail(String email);
    StudentEntity findByStudentId(Long studentId);
    List<StudentEntity> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
