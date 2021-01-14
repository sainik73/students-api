package com.example.openapi.students.controller;


import com.example.openapi.StudentsApi;
import com.example.openapi.model.Student;
import com.example.openapi.model.Students;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentsApiController implements StudentsApi {
    @Override
    public ResponseEntity<Students> studentsGet(String studentName) {
        //call DB to get resources by studentName
        //for now, mock the call
        Student s1 = new Student();
        s1.setStudentid(101);
        s1.setAge(23);
        s1.setName(studentName);

        Student s2 = new Student();
        s2.setStudentid(101);
        s2.setAge(23);
        s2.setName(studentName);

        Students studentsList = new Students();
        studentsList.add(s1);
        studentsList.add(s2);

        return new ResponseEntity<Students>(studentsList,HttpStatus.OK);
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
}
