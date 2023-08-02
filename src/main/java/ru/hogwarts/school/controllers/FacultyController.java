package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.services.FacultyService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return service.add(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> find(@PathVariable long id) {
        Faculty faculty = service.find(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> edit(@RequestBody Faculty faculty) {
        Faculty editFaculty = service.edit(faculty);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping
    public ResponseEntity<Faculty> delete(@RequestParam long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color")
    public Collection<Faculty> findByColor(@RequestParam String color) {
        return service.findByColor(color);
    }

    @GetMapping("/findByNameOrColor")
    public Collection<Faculty> findByNameOrColor(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String color) {
        return service.findByNameOrColor(name, color);

    }

    @GetMapping("{id}/students")
    public Collection<Student> findFacultyByStudent(@PathVariable Long id) {
        return service.findFacultyByStudent(id);
    }

    @GetMapping("/findLongestFacultyName")
    public Optional<String> getLongestFacultyName() {
       return service.getLongestFacultyName();
    }
}
