package com.example.openapi.students.service;

import com.example.openapi.students.db.model.Student;
import com.example.openapi.students.repository.StudentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class has the business logic to deal with requests and persist to storage
 */
@Service
@Slf4j
public class StudentsService {
    @Autowired
    private StudentsRepository studentsRepository;

    public com.example.openapi.model.Student create(int id, String name, int age){
        Student s = studentsRepository.insert(new Student(id, name, age));
        log.debug("Create in StudentsService: id:: {}",s.getStudentid());
        //wrap to json entity
        return convertToJSON(s);
    }

    public com.example.openapi.model.Student update(int id, String name, int age){
        Student s = studentsRepository.save(new Student(id, name, age));
        //wrap to json entity
        return convertToJSON(s);
    }

    public com.example.openapi.model.Student getById(Integer studentId){
        Optional<Student> s = studentsRepository.findById(studentId);
        return s.map(this::convertToJSON).orElse(null);
    }

    public List<com.example.openapi.model.Student> getByName(String studentName){
        List<Student> studentsList = studentsRepository.findByName(studentName);
        List<com.example.openapi.model.Student> jsonStudent = new ArrayList<>();
        for(Student student:studentsList){
            jsonStudent.add(convertToJSON(student));
        }
        return jsonStudent;
    }

    public List<com.example.openapi.model.Student> getByAge(Integer studentAge) {
        List<Student> studentsList = studentsRepository.findByAge(studentAge);
        List<com.example.openapi.model.Student> jsonStudent = new ArrayList<>();
        for (Student student : studentsList) {
            jsonStudent.add(convertToJSON(student));
        }
        return jsonStudent;
    }

    public List<com.example.openapi.model.Student> getAll() {
        List<Student> studentsList = studentsRepository.findAll();
        List<com.example.openapi.model.Student> jsonStudent = new ArrayList<>();
        for (Student student : studentsList) {
            jsonStudent.add(convertToJSON(student));
        }
        return jsonStudent;
    }

    /**
     * Method to convert the DB model object to json model
     * @param student Object of Student
     * @return com.example.openapi.model.Student
     */
    private com.example.openapi.model.Student convertToJSON(Student student) {
        com.example.openapi.model.Student openapiStudent = new com.example.openapi.model.Student();
        openapiStudent.setStudentid(student.getStudentid());
        openapiStudent.setName(student.getName());
        openapiStudent.setAge(student.getAge());
        log.debug("convertToJSON Student id: {}", openapiStudent.getStudentid());
        return openapiStudent;
    }
}
