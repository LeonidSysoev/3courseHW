package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {
    @Configuration
    public class TestDatabaseConfig {

        @Bean
        DataSource h2Database() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .build();
        }
    }

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void getFacultyByID() throws Exception {
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + 1, String.class))
                .isNotNull();
    }

    @Test
    void postFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Техфак");
        faculty.setColor("синий");
        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class))
                .isNotNull();
    }

    @Test
    void putFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Техфак");
        faculty.setColor("синий");
        HttpEntity<Faculty> facultyHttp = new HttpEntity<>(faculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT, facultyHttp, Faculty.class);
        Assertions.assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void deleteFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Техфак");
        faculty.setColor("синий");
        var s = this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/faculty?id=" + s.getId(),
                HttpMethod.DELETE, null, Void.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void findByColorFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Техфак");
        faculty.setColor("blue");
        Collection<Faculty> faculties = new ArrayList<>();
        faculties.add(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class));
        ResponseEntity<List<Faculty>> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty/color?color=" + "blue",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        Assertions.assertThat(response.getBody()).isEqualTo(faculties);
    }

    @Test
    void findByColorOrNameFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Техфак");
        faculty.setColor("синий");
        Faculty facultyNew = new Faculty();
        facultyNew.setName("Matfac");
        facultyNew.setColor("red");
        Collection<Faculty> faculties = new ArrayList<>();
        faculties.add(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, Faculty.class));
        faculties.add(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", facultyNew, Faculty.class));
        ResponseEntity<List<Faculty>> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty/findByNameOrColor?name=" + "Matfac",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        Assertions.assertThat(response.getBody().size()).isEqualTo(1);
    }

    @Test
    void getStudentByFacultyTest() {
        Student student = new Student();
        student.setName("Harry");
        student.setAge(24);
        Faculty faculty = new Faculty();
        faculty.setName("Grif");
        faculty.setColor("red");
        List<Student> students = new ArrayList<>();
        students.add(restTemplate.postForObject("http://localhost:" + port + "/student",
                student, Student.class));
        Faculty facultyOut = restTemplate.postForObject("http://localhost:" + port + "/faculty",
                faculty, Faculty.class);
        facultyOut.setStudents(students);
        ResponseEntity<List<Student>> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + facultyOut.getId() + "/students",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
