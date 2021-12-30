package com.example.studentmanager.service;

import com.example.studentmanager.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();

    void saveStudent(Student student);

    void updateStudent(Student student);

    Student getStudentById(Long id);

    void deleteStudent(Long id);
}
