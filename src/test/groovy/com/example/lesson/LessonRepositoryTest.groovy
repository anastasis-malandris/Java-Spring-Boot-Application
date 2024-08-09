package com.example.lesson

import com.example.lesson.dto.LessonCreationRequest
import com.example.lesson.model.Lesson
import com.example.lesson.repository.LessonRepository
import com.example.lesson.service.LessonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification
import spock.lang.Subject

@DataJpaTest
class LessonRepositoryTest extends Specification {

    @Autowired
    @Subject
    private LessonRepository lessonRepositoryTest

    private LessonService lessonService

    def setup() {
        lessonService = new LessonService(lessonRepositoryTest)
    }

    def tearDown() {
        lessonRepositoryTest.deleteAll()
    }

    def "Should be able to add a lesson and check that it exists"() {
        given: "I create a lesson"
        LessonCreationRequest lessonCreationRequest = new LessonCreationRequest("Mock Lesson Title")
        lessonService.createLesson(lessonCreationRequest)

        when: "I retrieve all lessons"
        List<Lesson> retrievedLessons = lessonRepositoryTest.findLessonByTitle("Mock Lesson Title")

        then: "I will get one lesson"
        retrievedLessons.size() == 1
    }

    def "Should be able to add 2 lessons with the same name and get both of them"() {
        given: "I create two lessons with the same title"
        LessonCreationRequest lessonCreationRequest1 = new LessonCreationRequest("Mock Lesson Title")
        lessonService.createLesson(lessonCreationRequest1)
        LessonCreationRequest lessonCreationRequest2 = new LessonCreationRequest("Mock Lesson Title")
        lessonService.createLesson(lessonCreationRequest2)

        when: "I retrieve all lessons"
        List<Lesson> retrievedLessons = lessonRepositoryTest.findLessonByTitle("Mock Lesson Title")

        then: "I get two lessons"
        retrievedLessons.size() == 2
    }

    def "Should get an empty list when trying to get a non existing lesson by title"() {
        given: "I create a lesson"
        LessonCreationRequest lessonCreationRequest = new LessonCreationRequest("Mock Lesson Title")
        lessonService.createLesson(lessonCreationRequest)

        when: "I retrieve all lesson by title"
        List<Lesson> retrievedLessons = lessonRepositoryTest.findLessonByTitle("Mock Lesson Title Does Not Exist")

        then: "Then I retrieve an empty list"
        retrievedLessons.isEmpty()
    }
}
