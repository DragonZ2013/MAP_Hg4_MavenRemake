package Main.Controller;

import Main.Repository.CourseRepository;
import Main.Repository.StudentRepository;
import Main.Repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @BeforeEach
    void setup() throws IOException {
        TeacherRepository tr = new TeacherRepository("TeacherDataTest.json");
        CourseRepository cr = new CourseRepository(tr,"CourseDataTest.json");
        StudentRepository sr = new StudentRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
    }

    @org.junit.jupiter.api.Test
    void updateTeacher() {
    }

    @org.junit.jupiter.api.Test
    void updateStudent() {
    }

    @org.junit.jupiter.api.Test
    void updateCourse() {
    }

    @org.junit.jupiter.api.Test
    void deleteCourse() {
    }

    @org.junit.jupiter.api.Test
    void deleteTeacher() {
    }

    @org.junit.jupiter.api.Test
    void deleteStudent() {
    }

    @org.junit.jupiter.api.Test
    void registerStudent() {
    }

    @org.junit.jupiter.api.Test
    void sortStudents() {
    }

    @org.junit.jupiter.api.Test
    void filterStudents() {
    }

    @org.junit.jupiter.api.Test
    void sortCourses() {
    }

    @org.junit.jupiter.api.Test
    void filterCourses() {
    }
}