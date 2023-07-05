package ru.hogwarts.school.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    StudentService service = new StudentService();

    @Test
    void add() {
        var actual = service.add(new Student("Ron", 15, 1));
        Student expected = new Student("Ron", 15, 1);
        assertThat(actual.getId()).isEqualTo(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void edit() {
        var expected = service.add(new Student("Ron", 15, 1));
        var actual = service.edit(1, new Student("Hermiona", 14, 1));
        assertThat(actual).isNotEqualTo(expected);
    }

    @Test
    void find() {
        var expected = service.add(new Student("Ron", 15, 1));
        Student actual = service.find(1);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void delete() {
        var actual = service.add(new Student("Harry", 18, 1));
        service.delete(1);
        assertThat(service.delete(1)).isNull();

    }

    @Test
    void getAll() {
        service.add(new Student("Harry", 18, 1));
        service.add(new Student("Ron", 16, 1));
        service.add(new Student("Hermiona", 18, 1));

        assertThat(service.getAll()).containsExactly(
                new Student("Harry", 18, 1),
                new Student("Ron", 16, 2),
                new Student("Hermiona", 18, 3)
        );

    }

    @Test
    void findByAge() {
        var expected = service.add(new Student("Ron", 16, 1));
        assertThat(service.findByAge(16)).containsExactly(expected);
    }
}