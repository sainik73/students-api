package com.example.openapi.students.repository;

import com.example.openapi.students.db.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsRepository extends MongoRepository<Student,Integer> {

   List<Student> findByName(String studentName);

   List<Student> findByAge(Integer studentAge);
}
