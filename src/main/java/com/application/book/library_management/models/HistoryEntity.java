package com.application.book.library_management.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "history")
public class HistoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="h_id")
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "s_id")
    private StudentEntity studentEntity;

    @ManyToOne
    @JoinColumn(name = "b_id")
    private BookEntity bookEntity;


    @Column(name = "issue_date")
    private LocalDate issueDate = LocalDate.now();

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "flag")
    private String flag;
}
