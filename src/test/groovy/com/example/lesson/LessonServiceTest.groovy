package com.example.lesson

import com.example.lesson.dto.LessonCreationRequest
import com.example.lesson.exceptions.LessonNotFoundException
import com.example.lesson.model.Lesson
import com.example.lesson.repository.LessonRepository
import com.example.lesson.service.LessonService
import spock.lang.Specification
import spock.lang.Subject

class LessonServiceTest extends Specification {

    @Subject
    private LessonService lessonServiceTest
    private LessonRepository lessonRepositoryTest
    private Lesson lesson

    void setup() {
        lessonRepositoryTest = Mock(LessonRepository)
        lessonServiceTest = new LessonService(lessonRepositoryTest)
    }

    def "Should be able to create a lesson"() {
        given: "There is a lesson creation request"
        String title = "Mocked lesson title"
        LessonCreationRequest lessonCreationRequest = new LessonCreationRequest(title)

        when: "I create a lesson"
        lessonServiceTest.createLesson(lessonCreationRequest)

        then: "Then the LessonRepository save method is called"
        1 * lessonRepositoryTest.save(new Lesson(null, title))

    }

    def "Should be able to get all lessons"() {
        when: "I retrieve all lessons"
        lessonServiceTest.getAllLessons()

        then: "All lessons are retrieved"
        1 * lessonRepositoryTest.findAll()
    }

    def "Should get a lesson by id when it exists"() {
        given:
        Integer id = 1
        lesson = Mock()
        lessonRepositoryTest.findById(id) >> Optional.of(lesson)

        when:
        lessonServiceTest.getLessonById(id)

        then:
        lessonRepositoryTest.findById(id)
    }

    def "Should get the Lesson not Found exception when getting lesson by id that does not exist"() {
        given:
        Integer id = 1
        lessonRepositoryTest.findById(id) >> Optional.empty()

        when:
        lessonServiceTest.getLessonById(id)

        then:
        def e = thrown(LessonNotFoundException)
        e.message == "Lesson with id " + id + " was not found."
    }

    def "Should get a lesson by title when it exists"() {
        given:
        String title = "Mocked Title"
        lesson = Mock()
        List<Lesson> retrievedLessons = Arrays.asList(lesson)

        lessonRepositoryTest.findLessonByTitle(title) >> retrievedLessons

        when:
        lessonServiceTest.getLessonsByTitle(title)

        then:
        lessonRepositoryTest.findLessonByTitle(title)
    }

    def "Should get the Lesson not Found exception when getting lesson by title that does not exist"() {
        given:
        String title = "Mocked title"

        lessonRepositoryTest.findLessonByTitle(title) >> Arrays.asList()

        when:
        lessonServiceTest.getLessonsByTitle(title)

        then:
        def e = thrown(LessonNotFoundException)
        e.message == "Lesson with title " + title + " was not found."
    }
}