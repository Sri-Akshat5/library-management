package com.application.book.library_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.book.library_management.dto.HistoryDto;
import com.application.book.library_management.models.HistoryEntity;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long>{
    
    List<HistoryEntity> findByStudentEntity_StudentId(Long studentId);
    List<HistoryEntity> findByBookEntity_BookId(Long bookId);
    List<HistoryEntity> findByFlagIgnoreCase(String flag);
    // List<HistoryEntity> findBookByFirstName(String firstName);
    boolean existsByStudentEntity_StudentIdAndBookEntity_BookIdAndFlag(Long studentId, Long bookId, String flag);

}
