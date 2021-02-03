package com.example.openapi.students.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String str, int id) {
        super("Student not found with " + str + ": " + id);
    }

    public StudentNotFoundException(String str, String studentName) {
        super("Student not found with " + str + ": " + studentName);
    }

    /**
     * Default constructor
     */
    public StudentNotFoundException() {

    }
}
