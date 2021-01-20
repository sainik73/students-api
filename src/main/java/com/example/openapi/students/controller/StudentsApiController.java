package com.example.openapi.students.controller;


import com.example.openapi.StudentsApi;
import com.example.openapi.StudentsByAgeApi;
import com.example.openapi.model.Student;
import com.example.openapi.model.Students;
import com.example.openapi.students.service.StudentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * This is the REST controller class for the Students API
 */
@RestController
@Slf4j
public class StudentsApiController implements StudentsApi , StudentsByAgeApi {
    @Autowired
    private StudentsService studentsService;

    @Override
    public ResponseEntity<Void> studentsPost(Student body) {
        //validate request and call DB to create resource
        Student s = studentsService.create(body.getStudentid(), body.getName(), body.getAge());
        log.debug("Successfully Created the student: {}",s.getStudentid());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> studentsPut(Student body) {
        //validate request and call DB to create resource
        Student s = studentsService.update(body.getStudentid(), body.getName(), body.getAge());
        log.debug("Successfully Updated the student: {}",s.getStudentid());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Student> studentsStudentIdGet(Integer studentId) {
        //call DB to get resource by studentId
        Student s = studentsService.getById(studentId);
        Predicate<Student> nonNullPredicate = Objects::nonNull;
        ResponseEntity<Student> responseEntity = null;
        if (nonNullPredicate.test(s)) {
            log.debug("Got Students by Id: {}, Name:{} ", new Object[]{s.getStudentid(), s.getName()});
            responseEntity = new ResponseEntity<>(s, HttpStatus.OK);
        }else{
            log.debug("Student not found by Id: {}", studentId);
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @Override
    public ResponseEntity<Students> studentsGet(String studentName) {
        //call DB to get resources by studentName
        List<Student> studentsList = studentsService.getByName(studentName);
        log.debug("Got Students by Name: {}, List Size:{} ", new Object[]{studentName,studentsList.size()});

        Students students = new Students();
        students.addAll(studentsList);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Students> studentsByAgeGet(@NotNull @Valid Integer studentAge) {
        //call DB to get resources by studentAge
        List<Student> studentsList = studentsService.getByAge(studentAge);
        log.debug("Got Students by Age: {}, List Size:{} ", new Object[]{studentAge,studentsList.size()});

        Students students = new Students();
        students.addAll(studentsList);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

}