package com.example.openapi.students.controller;


import com.example.openapi.StudentsApi;
import com.example.openapi.model.Student;
import com.example.openapi.model.Students;
import com.example.openapi.students.exception.StudentNotFoundException;
import com.example.openapi.students.service.StudentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.function.Predicate;

/**
 * This is the REST controller class for the Students API
 */
@RestController
@Slf4j
@Validated
public class StudentsApiController implements StudentsApi {
    @Autowired
    private StudentsService studentsService;

    private final Predicate<List<Student>> zeroSizePredicate =
                        studentsList -> (studentsList.size() == 0);


    /**
     * This method implements default get endpoint
     * for ping
     */
    @RequestMapping(value = "/info/ping",
            produces = { "application/text" },
            method = RequestMethod.GET)
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Void> createStudent(@Valid Student body) {
        //validate request and call DB to create resource
        Student s = studentsService.create(body.getStudentid(), body.getName(), body.getAge());
        log.debug("Successfully Created the student: {}",s.getStudentid());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateStudent(@Valid Student body) {
        //validate request and call DB to create resource
        Student s = studentsService.update(body.getStudentid(), body.getName(), body.getAge());
        log.debug("Successfully Updated the student: {}",s.getStudentid());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Student> findStudentById(Integer studentId) {
        //call DB to get resource by studentId
        Student s = studentsService.getById(studentId);
        Predicate<Student> nonNullPredicate = Objects::nonNull;
        ResponseEntity<Student> responseEntity;
        if (nonNullPredicate.test(s)) {
            log.debug("Got Students by Id: {}, Name:{} ", s.getStudentid(), s.getName());
            responseEntity = new ResponseEntity<>(s, HttpStatus.OK);
        }else{
            log.debug("No Student found using Id: {}", studentId);
            throw new StudentNotFoundException("Id", studentId);
        }

        return responseEntity;
    }

    @Override
    public ResponseEntity<Students> findStudentsByName(@NotNull @Valid String studentName) {
        //call DB to get resources by studentName
        List<Student> studentsList = studentsService.getByName(studentName);
        if(zeroSizePredicate.test(studentsList)) {
            throw new StudentNotFoundException("Name", studentName);
        }
        log.debug("Got Students by Name: {}, List Size:{} ", studentName,studentsList.size());
        Students students = new Students();
        students.addAll(studentsList);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Students> findAllStudents() {
        //call DB to get all resources
        List<Student> studentsList = studentsService.getAll();
        if(zeroSizePredicate.test(studentsList)) {
            throw new StudentNotFoundException();
        }
        log.debug("Got Students List Size:{} ", studentsList.size());
        Students students = new Students();
        students.addAll(studentsList);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Students> findStudentsByAge(@NotNull @Valid Integer studentAge) {
        //call DB to get resources by studentAge
        List<Student> studentsList = studentsService.getByAge(studentAge);
        if(zeroSizePredicate.test(studentsList)) {
            throw new StudentNotFoundException("Age", studentAge);
        }
        log.debug("Got Students by Age: {}, List Size:{} ", studentAge,studentsList.size());
        Students students = new Students();
        students.addAll(studentsList);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return errors;
    }

}