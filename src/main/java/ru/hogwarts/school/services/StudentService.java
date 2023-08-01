package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student) {
        logger.info("Called method add with argument {}", student);
        return studentRepository.save(student);
    }

    public Student edit(Student student) {
        logger.info("Called method edit with argument {}", student);
        return studentRepository.save(student);
    }

    public Student find(long id) {
        logger.info("Called method find with argument {}", id);
        return studentRepository.findById(id).get();
    }

    public void delete(long id) {
        logger.info("Called method delete with argument {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAll() {
        logger.info("Called method getAll");
        return Collections.unmodifiableCollection(studentRepository.findAll());
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Called method findByAge with argument {}", age);
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Called method findByAgeBetween with argument minAge {}, maxAge{}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty findFacultyByStudents(Long id) {
        logger.info("Called method findFacultyByStudents with argument {}", id);
        return studentRepository.findById(id).map(e -> e.getFaculty()).orElse(null);
    }

    public int getAllQuantity() {
        logger.info("Called method getAllQuantity");
        return studentRepository.getAllQuantity();
    }

    public long getAverageAge() {
        logger.info("Called method getAverageAge");
        return studentRepository.getAverageAge();
    }

    public Collection<Student> getLastFiveStudents() {
        logger.info("Called method getLastFiveStudents");
        return studentRepository.getLastFiveStudents();
    }


    public Collection<String> getAllNameStartsA() {
        logger.info("Called method getAllNameStartsA");
        return getAll()
                .stream()
                .filter(e -> e.getName().startsWith("А"))
                .map(e -> e.getName().toUpperCase())
                .sorted()
                .toList();
    }

    public double getStudentAverageAge() {
        logger.info("Called method getStudentAverageAge");
        return getAll()
                .stream()
                .mapToDouble(Student::getAge)
                .average().getAsDouble();

    }

    public int findSum() {
        logger.info("Called method findSum");
        long start = System.currentTimeMillis();
        int sum = Stream
                .iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        long elapsed = finish - start;
        System.out.println("Прошло времени, мс: " + elapsed);
        return sum;
    }
}
