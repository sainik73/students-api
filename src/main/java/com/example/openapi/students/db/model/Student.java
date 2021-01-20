package com.example.openapi.students.db.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
/**
 * This class represents the Student entity
 */
@Document
@Data
public class Student   {
    @Id
    private Integer studentid;

    private String name;

    private Integer age;

    /**
     * Default constructor
     */
    public Student(){}

    /**
     * Custom constructor
     * @param id    id of student
     * @param name  name of student
     * @param age   age of student
     */
    public Student(Integer id, String name, Integer age) {
        this.studentid = id;
        this.name = name;
        this.age = age;
    }
}
