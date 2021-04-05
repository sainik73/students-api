package com.example.openapi.students.repository;

import com.example.openapi.students.db.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for Student Repository to test custom methods
 * @author Kiran
 * @since 4/3/2021
 */
@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@Slf4j
class StudentsRepositoryTest {
    /* Variable to ensure bootstrapping of repository is done only once */
    private static boolean bootstrapped = false;
    @Container
    static MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:4.4.4");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        log.info("spring.data.mongodb.uri -> " + mongoDBContainer.getReplicaSetUrl());
    }

    @Autowired
    private StudentsRepository studentsRepository;

    @BeforeEach
    public void beforeSetupTest(){
        bootstrapRepository();
    }

    @AfterClass
    public static void cleanUp(){
        mongoDBContainer.stop();
    }

    @Test
    void testFindByName(){
        List<Student> studentList = this.studentsRepository.findByName("Test");
        assertEquals(1, studentList.size());
    }

    @Test
    void testFindByAge(){
        List<Student> studentList = this.studentsRepository.findByAge(22);
        assertEquals(2, studentList.size());
    }

    /**
     * Load the repository with data
     */
    private void bootstrapRepository(){
        if(!bootstrapped) {
            log.info("Before Bootstrapping the repository........");
            Student student = new Student();
            student.setStudentid(1);
            student.setName("Test");
            student.setAge(22);
            //this.studentsRepository.save(student);

            Student student2 = new Student();
            student2.setStudentid(2);
            student2.setName("Test2");
            student2.setAge(22);
            List<Student> studentList = Arrays.asList(student, student2);
            this.studentsRepository.insert(studentList);
            bootstrapped = true;
            log.info("Bootstrapped the repository........");
        }

    }

}
