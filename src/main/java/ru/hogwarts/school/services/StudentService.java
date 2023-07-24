package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student) {
        return studentRepository.save(student);
    }

    public Student edit(Student student) {
        return studentRepository.save(student);
    }

    public Student find(long id) {
        return studentRepository.findById(id).get();
    }

    public void delete(long id) {
       studentRepository.deleteById(id);
    }

    public Collection<Student> getAll() {

        return Collections.unmodifiableCollection(studentRepository.findAll());
    }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty findFacultyByStudents(Long id) {
        return studentRepository.findById(id).map(e -> e.getFaculty()).orElse(null);
    }

    public int getAllQuantity() {
        return studentRepository.getAllQuantity();
    }

    public long getAverageAge() {
        return  studentRepository.getAverageAge();
    }

    public Collection<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }



}
