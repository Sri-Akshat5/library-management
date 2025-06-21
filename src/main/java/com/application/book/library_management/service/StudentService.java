package com.application.book.library_management.service;

import java.util.List;

import com.application.book.library_management.dto.StudentDto;

public interface  StudentService {

    public StudentDto saveStudent(StudentDto studentDto);

    public List<StudentDto> getAllStudents();

    public StudentDto updateStudent(Long studentId, StudentDto studentDto);

    public void deleteStudent(Long studentId);

    List<StudentDto> searchStudentsByName(String keyword);

    StudentDto getStudentById(Long studentId);



}
