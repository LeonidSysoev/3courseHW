package ru.hogwarts.school.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepositoryMock;
    private StudentService service;

    @BeforeEach
    void setUp() {
        service = new StudentService(studentRepositoryMock);
    }


    @Test
    void add() {
        when(studentRepositoryMock.save(new Student("Harry", 20, 1L)))
                .thenReturn(new Student("Harry", 20, 1L));
        var actual = service.add(new Student("Harry", 20, 1L));
        assertEquals(1, actual.getId());
        assertEquals(new Student("Harry", 20, 1L), actual);
    }

    @Test
    void edit() {
        when(studentRepositoryMock.save(any()))
                .then(invocation -> invocation.getArguments()[0]);
        var actual = service.edit(new Student("Ron", 19, 1));
        assertThat(actual.getAge()).isEqualTo(19);
    }

    @Test
    void find() {
        when(studentRepositoryMock.findById(1L))
                .thenReturn(Optional.of(new Student("Harry", 20, 1)));
        var actual = service.find(1);
        assertEquals(new Student("Harry", 20, 1), actual);
    }

    @Test
    void delete() {
        service.delete(1L);
        verify(studentRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    void getAll() {
        when(studentRepositoryMock.findAll())
                .thenReturn(List.of(
                        (new Student("Harry", 18, 1)),
                        (new Student("Ron", 16, 2)),
                        (new Student("Hermiona", 18, 3))));

        assertThat(service.getAll()).containsExactly(
                new Student("Harry", 18, 1),
                new Student("Ron", 16, 2),
                new Student("Hermiona", 18, 3)
        );

    }

    @Test
    void findByAge() {
        when(studentRepositoryMock.findAll())
                .thenReturn(List.of(
                        (new Student("Harry", 20, 1)),
                        (new Student("Ron", 16, 2)),
                        (new Student("Hermiona", 18, 3))));
        var actual = service.findByAge(20);
        assertThat(actual.size()).isEqualTo(1);
    }
}

