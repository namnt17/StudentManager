package com.example.studentmanager.controller;


import com.example.studentmanager.entity.Student;
import com.example.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/students")
public class StudentAPIController {

    @Autowired
    private final StudentService studentService;

    public StudentAPIController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Get All Students
    @GetMapping()
    public List<Student> listAll(){
        return studentService.getStudents();
    }

    // Get Student By ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        try {
            Student student = studentService.getStudentById(id);
            return new ResponseEntity<>(student, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Add new Student
    @PostMapping
    public void addNewStudent(@RequestBody Student student){
        studentService.saveStudent(student);
    }

    //Update Student By ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudentById(@RequestBody Student student, @PathVariable Long id){
        try {
            Student existStudent = studentService.getStudentById(id);
            existStudent.setFirstName(student.getFirstName());
            existStudent.setLastName(student.getLastName());
            existStudent.setEmail(student.getEmail());
            studentService.saveStudent(existStudent);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Delete Student By ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Long id){
        try {
            studentService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
