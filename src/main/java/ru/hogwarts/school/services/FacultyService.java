package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;


import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final  HashMap<Long, Faculty> faculties = new HashMap<>();
    private long idCount = 0;
    public Faculty add(Faculty faculty) {
        faculty.setId(++idCount);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty edit(long id, Faculty faculty) {
        if (faculties.containsKey(id)) {
            faculties.put(id, faculty);
            return faculty;
        }
        return null;
    }

    public Faculty find(long id) {
        return faculties.get(id);
    }

    public Faculty delete(long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> findByColor(String color) {
        return faculties.values().stream()
                .filter(e->e.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
