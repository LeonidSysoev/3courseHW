package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.FacultyService;
import ru.hogwarts.school.services.StudentService;

import java.util.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTestWithMocks {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private FacultyService facultyService;

    @Test
    public void saveGetByIdDeleteStudentTest() throws Exception {
        String name = "Harry";
        int age = 20;
        long id = 1L;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.id").value(id));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.id").value(id));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.id").value(id));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllTest() throws Exception {
        String name = "Harry";
        int age = 20;
        long id = 1L;
        List<Student> students = new ArrayList<>(
                Arrays.asList(new Student(name, age, id)));
        when(studentRepository.findAll()).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(students.size()));
    }

    @Test
    public void findByAgeTest() throws Exception {
        int age = 22;
        List<Student> students = new ArrayList<>(
                Arrays.asList(
                        new Student("Harry", 20, 1),
                        new Student("Ron", 21, 2),
                        new Student("Drako", 22, 3)));
        when(studentRepository.findAllByAge(age)).thenReturn(Arrays.asList(new Student("Drako", 22, 3)));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age?age=22")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andDo(print());
    }

    @Test
    public void findBetweenAgeTest() throws Exception {

        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class)))
                .thenReturn(Arrays.asList(
                        new Student("Harry", 20, 1),
                        new Student("Ron", 21, 2)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/ageBetween?minAge=20&maxAge=21")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andDo(print());
    }

    @Test
    public void getFacultyByStudentTest() throws Exception {
        String name = "Harry";
        int age = 20;
        long id = 1L;
        String facultyName = "Техфак";
        String facultycolor = "color";
        long facultyId = 1L;
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("faculty", facultyName);
        facultyObject.put("color", facultycolor);
        Faculty faculty = new Faculty();
        faculty.setName(facultyName);
        faculty.setColor(facultycolor);
        faculty.setId(facultyId);
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setId(id);
        student.setFaculty(faculty);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + facultyId + "/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andDo(print());
    }


}
