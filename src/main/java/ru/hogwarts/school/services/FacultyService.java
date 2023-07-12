package ru.hogwarts.school.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(Faculty faculty) {

        return facultyRepository.save(faculty);
    }

    public Faculty edit(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty find(long id) {
        return facultyRepository.findById(id).get();
    }

    public void delete(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findAllByColor(color);
    }

    public Collection<Faculty> findByNameOrColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> findFacultyByStudent(Long id) {
        return facultyRepository.findById(id).get().getStudents();
    }
}
