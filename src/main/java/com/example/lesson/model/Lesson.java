package com.example.lesson.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lesson {

    @Id
    @SequenceGenerator(
            name = "lesson_id_sequence",
            sequenceName = "lesson_id_sequence"
    )
    @GeneratedValue(
            strategy =  GenerationType.SEQUENCE,
            generator = "lesson_id_sequence"
    )
    private Integer id;

    @NotBlank(message = "Title should be non empty")
    @NotNull(message = "Title should be non null")
    private String lessonTitle;

}
