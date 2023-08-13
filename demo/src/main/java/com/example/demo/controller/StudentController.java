package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import com.example.demo.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/student")
public class StudentController {
    @Autowired
    private StudentService studentService ;

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public String registerNewStudent(@RequestBody Student student) throws NotFoundException{
        studentService.registerNewStudent(student);
        return "Student has been saved";
    }

    @DeleteMapping(path = "{id}")
    public String deleteStudent(@PathVariable("id") Long studentId) {
        studentService.deleteStudent(studentId);
        return "Student has been deleted :)";
    }

    @PutMapping(path = "{id}")
    public Student updateStudent(@PathVariable("id") Long id , @RequestBody Student student) throws NotFoundException {
        return studentService.updateStudent(id , student);
    }

}
