package com.example.studentmanager.controller;

import com.example.studentmanager.entity.Student;
import com.example.studentmanager.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public ModelAndView getStudents(Model model) {
        model.addAttribute("students", studentService.getStudents());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("student");
        return modelAndView;
    }

    @GetMapping("/students/new")
    public String addNewStudent(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        // bind kết quả trả về của một method thành model attribute
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    // annotation @PathVariable sẽ là đối chiếu với tham số trên URL trả về
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student) {
        Student existingStudent = studentService.getStudentById(id);
        // get student from database by id
        existingStudent.setId(id);
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        // save updated student object
        studentService.updateStudent(existingStudent);
        return "redirect:/students";
    }

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
