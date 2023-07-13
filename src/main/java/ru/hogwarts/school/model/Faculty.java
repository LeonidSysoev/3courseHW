package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class Faculty {
    private String name;
    private String color;
    @Id
    @GeneratedValue
    private long id;




    @OneToMany(mappedBy = "faculty")
    @JsonIgnore
    List<Student> students;

    public Faculty(String name, String color, long id) {
        this.name = name;
        this.color = color;
        this.id = id;
    }

    public Faculty() {

    }


    @Override
    public String toString() {
        return "Faculty{" +
                "name='" + name + '\'' +
                ", age=" + color +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty faculty)) return false;
        return id == faculty.id && Objects.equals(name, faculty.name) && Objects.equals(color, faculty.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Student> getStudents() {
        return students;
    }
}
