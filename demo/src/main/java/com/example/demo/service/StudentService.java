package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.error.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository ;

    public List<Student> getStudents() {
        return studentRepository.findAll() ;
    }

    public String registerNewStudent(Student student) throws NotFoundException{
        Optional<Student> studentOptional = studentRepository.getStudentsByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new NotFoundException("This email is taken before!");
        }
        studentRepository.save(student);
        return "Student has been saved";
    }

    public String deleteStudent(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        System.out.println(studentOptional);
        if(!studentOptional.isPresent()) {
            throw new IllegalStateException("The id " + studentId + " doesn't exist!");
        }
        studentRepository.deleteById(studentId);
        return "Student has been deleted :)";
    }

    public Student updateStudent(Long id , Student student) throws NotFoundException {

        Student originStudent = studentRepository.findById(id).get() ;

        if(Objects.nonNull(student.getName()) &&
        !"".equalsIgnoreCase(student.getName())) {
            originStudent.setName(student.getName());
        }
        if(Objects.nonNull(student.getEmail()) &&
                !"".equalsIgnoreCase(student.getEmail())) {
            Optional<Student> studentOptional = studentRepository.getStudentsByEmail(student.getEmail());
            if(studentOptional.isPresent()){
                throw new NotFoundException("This email is taken before!");
            }
            originStudent.setEmail(student.getEmail());
        }
        if(Objects.nonNull(student.getAge()) &&
                student.getAge() != 0) {
            originStudent.setAge(student.getAge());
        }
        if(Objects.nonNull(student.getDateOfBirth()) &&
                isValidDate(student.getDateOfBirth())) {
            originStudent.setDateOfBirth(student.getDateOfBirth());
        }

        return studentRepository.save(originStudent);

    }
    public Boolean isValidDate(LocalDate date) {
        try {
            LocalDate.parse(date.toString());
            return true ;
        }catch (DateTimeParseException e){
            return false ;
        }
    }
}
