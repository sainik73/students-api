package com.example.openapi.students.controller;


import com.example.openapi.StudentsApi;
import com.example.openapi.StudentsByAgeApi;
import com.example.openapi.model.Student;
import com.example.openapi.model.Students;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class StudentsApiController implements StudentsApi , StudentsByAgeApi {
    @Override
    public ResponseEntity<Students> studentsGet(String studentName) {
        //call DB to get resources by studentName
        //for now, mock the call
        Student s1 = new Student();
        s1.setStudentid(101);
        s1.setAge(23);
        s1.setName(studentName);

        Student s2 = new Student();
        s2.setStudentid(102);
        s2.setAge(22);
        s2.setName(studentName);

        Students studentsList = new Students();
        studentsList.add(s1);
        studentsList.add(s2);

        return new ResponseEntity<>(studentsList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> studentsPost(Student body) {
        //validate request and call DB to create resource
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> studentsPut(Student body) {
        //validate request and call DB to create resource
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Student> studentsStudentIdGet(Integer studentId) {
        //call DB to get resource by studentId
        //for now, mock the call
        Student s = new Student();
        s.setStudentid(studentId);
        s.setAge(23);
        s.setName("Alex");

        return new ResponseEntity<>(s,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Students> studentsByAgeGet(@NotNull @Valid Integer studentAge) {
        //call DB to get resources by studentAge
        //for now, mock the call
        Student s1 = new Student();
        s1.setStudentid(101);
        s1.setAge(studentAge);
        s1.setName("Alex");

        Student s2 = new Student();
        s2.setStudentid(102);
        s2.setAge(studentAge);
        s2.setName("Robert");

        Students studentsList = new Students();
        studentsList.add(s1);
        studentsList.add(s2);

        return new ResponseEntity<>(studentsList,HttpStatus.OK);
    }

}