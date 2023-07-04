package ru.hogwarts.school.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {
    FacultyService facultyService = new FacultyService();

    @Test
    void add() {
        Faculty actual = facultyService.add(new Faculty("Harry", "red", 1));
        assertEquals(1, actual.getId());
        var expected = new Faculty("Harry", "red", 1L);
        assertEquals(expected, actual);
    }

    @Test
    void edit() {
        Faculty actual = facultyService.edit(1, new Faculty("Harry", "red",1));
        var expected = new Faculty("Ron", "red", 1);
        assertNotEquals(expected, actual);
    }

    @Test
    void find() {
        Faculty expected = facultyService.add(new Faculty("Harry", "red", 1));
        Faculty actual = facultyService.find (1);
        assertEquals(expected, actual);
    }

    @Test
    void delete() {
        Faculty actual = facultyService.add(new Faculty("Harry", "red", 1));
        facultyService.delete(1);
        assertThat(facultyService.delete(1)).isNull();
    }

    @Test
    void findByColor() {
        Faculty actual = facultyService.add(new Faculty("Harry", "red", 1));
        assertThat(facultyService.findByColor("red")).containsExactly(actual);
    }
}