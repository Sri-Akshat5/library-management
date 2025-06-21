package com.application.book.library_management.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.book.library_management.dto.StudentDto;
import com.application.book.library_management.exception.BookAlreadyBorrowedException;
import com.application.book.library_management.exception.DuplicateEmailException;
import com.application.book.library_management.exception.DuplicateIsbnException;
import com.application.book.library_management.exception.StudentHasActiveBorrowingsException;
import com.application.book.library_management.exception.StudentNotFoundException;
import com.application.book.library_management.interceptor.Loggers;
import com.application.book.library_management.models.StudentEntity;
import com.application.book.library_management.repository.StudentRepository;
import com.application.book.library_management.service.StudentService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final Loggers loggers;

    @Autowired
    public StudentServiceImpl() {
        this.loggers = new Loggers(StudentServiceImpl.class);
    }

    @Override
    public StudentDto saveStudent(StudentDto studentDto) {
        loggers.info("Attempting to save student with email: " + studentDto.getEmail());

        if (studentRepository.findByEmail(studentDto.getEmail()) != null) {
            loggers.error("Duplicate email found: " + studentDto.getEmail());
            throw new DuplicateEmailException("Student with this email already exists.");
        }

        StudentEntity studentEntity = modelMapper.map(studentDto, StudentEntity.class);
        StudentDto savedDto = modelMapper.map(studentRepository.save(studentEntity), StudentDto.class);

        loggers.info("Student saved successfully with ID: " + savedDto.getStudentId());
        return savedDto;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        loggers.info("Fetching all students");
        List<StudentDto> students = studentRepository.findAll()
                .stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());

        loggers.debug("Total students fetched: " + students.size());
        return students;
    }

    @Override
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) {
        loggers.info("Attempting to update student with ID: " + studentId);

        StudentEntity existing = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    loggers.error("Student not found with ID: " + studentId);
                    return new StudentNotFoundException("Student not found");
                });

        if (!existing.getEmail().equals(studentDto.getEmail())) {
            StudentEntity emailConflict = studentRepository.findByEmail(studentDto.getEmail());
            if (emailConflict != null) {
                loggers.error("Duplicate email found while updating: " + studentDto.getEmail());
                throw new DuplicateIsbnException("Book with this ISBN already exists.");
            }
        }

        existing.setFirstName(studentDto.getFirstName());
        existing.setLastName(studentDto.getLastName());
        existing.setEmail(studentDto.getEmail());

        StudentDto updated = modelMapper.map(studentRepository.save(existing), StudentDto.class);
        loggers.info("Student updated successfully with ID: " + updated.getStudentId());
        return updated;
    }

    @Override
    public void deleteStudent(Long studentId) {
        loggers.info("Attempting to delete student with ID: " + studentId);

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    loggers.error("Student not found with ID: " + studentId);
                    return new StudentNotFoundException("Student not found");
                });

        if (!student.getHistoryEntity().isEmpty()) {
            loggers.warn("Cannot delete student with active borrowings. ID: " + studentId);
            throw new StudentHasActiveBorrowingsException("Student has active borrowing history and cannot be deleted.");
        }

        studentRepository.delete(student);
        loggers.info("Student deleted successfully. ID: " + studentId);
    }

    @Override
    public List<StudentDto> searchStudentsByName(String keyword) {
        loggers.info("Searching students by keyword: " + keyword);
        List<StudentDto> results = studentRepository.findAll()
                .stream()
                .filter(student ->
                        (student.getFirstName() + " " + student.getLastName())
                                .toLowerCase()
                                .contains(keyword.toLowerCase()))
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());

        loggers.debug("Students found with keyword '" + keyword + "': " + results.size());
        return results;
    }

    @Override
    public StudentDto getStudentById(Long studentId) {
        loggers.info("Fetching student by ID: " + studentId);

        StudentEntity entity = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    loggers.error("Student not found with ID: " + studentId);
                    return new StudentNotFoundException("Student not found");
                });

        loggers.info("Student found: " + entity.getFirstName() + " " + entity.getLastName());
        return modelMapper.map(entity, StudentDto.class);
    }
}
