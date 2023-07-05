package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final HashMap<Long, Student> students = new HashMap<>();
    private long idCount = 0;

    public Student add(Student student) {
        student.setId(++idCount);
        students.put(student.getId(), student);
        return student;
    }

    public Student edit(long id, Student student) {
        if (students.containsKey(id)) {
            students.put(id, student);
            return student;
        }
        return null;
    }

    public Student find(long id) {
        return students.get(id);
    }

    public Student delete(long id) {
        return students.remove(id);
    }

    public Collection<Student> getAll() {
        return Collections.unmodifiableCollection(students.values());
    }

    public Collection<Student> findByAge(int age) {
        return students.values().stream()
                .filter(e -> e.getAge() == age)
                .collect(Collectors.toList());
    }
}
