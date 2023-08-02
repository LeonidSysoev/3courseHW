package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(Faculty faculty) {
        logger.info("Called method add with argument {}", faculty);

        return facultyRepository.save(faculty);
    }

    public Faculty edit(Faculty faculty) {
        logger.info("Called method edit with argument {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty find(long id) {
        logger.info("Called method find with argument", id);
        return facultyRepository.findById(id).get();
    }

    public void delete(long id) {
        logger.info("Called method delete with argument {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("Called method findByColor with argument {}", color);
        return facultyRepository.findAllByColor(color);
    }

    public Collection<Faculty> findByNameOrColor(String name, String color) {
        logger.info("Called method findByNameOrColor with argument name {}, color {}", name, color);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public Collection<Student> findFacultyByStudent(Long id) {
        logger.info("Called method findFacultyByStudent with argument {}", id);
        return facultyRepository.findById(id).map(Faculty::getStudents).orElse(Collections.emptyList());
    }

    public Optional<String> getLongestFacultyName() {
        logger.info("Called method getLongestFacultyName");
        return facultyRepository.findAll()
                .stream()
                .map(e -> e.getName())
                .max(Comparator.comparing(String::length));

    }
}
