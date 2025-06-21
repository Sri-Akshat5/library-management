package com.application.book.library_management.dto;

import jakarta.persistence.Column;
import lombok.Data;


@Data
public class StudentDto {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
}
