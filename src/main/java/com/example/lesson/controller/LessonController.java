package com.example.lesson.controller;

import com.example.lesson.dto.LessonCreationRequest;
import com.example.lesson.exceptions.LessonNotFoundException;
import com.example.lesson.model.Lesson;
import com.example.lesson.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/lessons")
public record LessonController(LessonService lessonService) {

    @PostMapping
    public void createLesson(@RequestBody LessonCreationRequest lessonCreationRequest){
        log.info("new lesson was inserted {}", lessonCreationRequest);
        lessonService.createLesson(lessonCreationRequest);
    }

    @GetMapping("/all")
    List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    Lesson getLessonById(@PathVariable Integer id) throws LessonNotFoundException {
        return lessonService.getLessonById(id);
    }

    @GetMapping
    List<Lesson> getLessonsByTitle(@RequestParam(value = "title") String lessonTitle) throws LessonNotFoundException {
        return lessonService.getLessonsByTitle(lessonTitle);
    }
}

