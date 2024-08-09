package com.example.lesson.repository;

import com.example.lesson.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    @Query(value = "SELECT t FROM Lesson t WHERE t.lessonTitle = :title")
    List<Lesson> findLessonByTitle(@Param("title") String lessonTitle);
}
