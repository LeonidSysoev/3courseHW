package ru.hogwarts.school.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    @Mock
    private FacultyRepository facultyRepositoryMock;
    private FacultyService service;

    @BeforeEach
    void setUp() {
        service = new FacultyService(facultyRepositoryMock);
    }


    @Test
    void add() {
        when(facultyRepositoryMock.save(new Faculty("Harry", "red", 1L)))
                .thenReturn(new Faculty("Harry", "red", 1L));
        ;
        var actual = service.add(new Faculty("Harry", "red", 1L));
        assertEquals(1, actual.getId());
        assertEquals(new Faculty("Harry", "red", 1L), actual);
    }

    @Test
    void edit() {
        when(facultyRepositoryMock.save(any()))
                .then(invocation -> invocation.getArguments()[0]);
        var actual = service.edit(new Faculty("Ron", "blue", 1));
        assertThat(actual.getColor()).isEqualTo("blue");
    }

    @Test
    void find() {
        when(facultyRepositoryMock.findById(1L))
                .thenReturn(Optional.of(new Faculty("Harry", "red", 1)));
        var actual = service.find(1);
        assertEquals(new Faculty("Harry", "red", 1), actual);
    }

    @Test
    void delete() {
        service.delete(1L);
        verify(facultyRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    void findByColor() {
        when(facultyRepositoryMock.findAll())
                .thenReturn(List.of(
                        new Faculty("Harry", "red", 1),
                        new Faculty("Ron", "blue", 2)));
        var actual = service.findByColor("red");
        assertThat(actual.size()).isEqualTo(1);


    }
}