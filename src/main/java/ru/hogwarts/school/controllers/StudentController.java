package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;


    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public Student add(@RequestBody Student student) {

        return service.add(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> find(@PathVariable long id) {
        Student student = service.find(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> edit(@RequestBody Student student) {
        Student editStudent = service.edit(student);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(editStudent);
    }

    @DeleteMapping
    public ResponseEntity<Student> delete(@RequestParam long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public Collection<Student> all() {
        return service.getAll();
    }

    @GetMapping("age")
    public Collection<Student> findByAge(@RequestParam int age) {
        return service.findByAge(age);
    }

    @GetMapping("/ageBetween")
    public Collection<Student> findByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        return service.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("{id}/faculty")
    public Faculty findFacultyByStudents(@PathVariable Long id) {

        return service.findFacultyByStudents(id);
    }

    @GetMapping("/quantity")
    public int getAllQuantity() {
        return service.getAllQuantity();
    }

    @GetMapping("/average-age")
    public long getAverageAge() {
        return service.getAverageAge();
    }

    @GetMapping("/last-students")
    public Collection<Student> getLastFiveStudents() {
        return service.getLastFiveStudents();
    }

    @GetMapping("/name-starts-A")
    public Collection<String> getAllNameStartsA() {
        return service.getAllNameStartsA();
    }

    @GetMapping("/student-average-age")
    public double getStudentAverageAge() {
        return service.getStudentAverageAge();
    }

    @GetMapping("/findSum")
    public int findSum() {
        return service.findSum();
    }

    @GetMapping("/print-students")
    public void printInConsole() {
        service.printInConsole();
    }
    @GetMapping("/print-sync-students")
    public void printSyncInConsole() {
        service.printSyncInConsole();
    }
}
