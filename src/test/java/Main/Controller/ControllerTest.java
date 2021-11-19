package Main.Controller;

import Main.Exceptions.MaxSizeException;
import Main.Exceptions.MissingIdException;
import Main.Model.Course;
import Main.Repository.CourseRepository;
import Main.Repository.StudentRepository;
import Main.Repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {


    /*@BeforeEach
    void setup() throws IOException {
        TeacherRepository tr = new TeacherRepository("TeacherDataTest.json");
        CourseRepository cr = new CourseRepository(tr,"CourseDataTest.json");
        StudentRepository sr = new StudentRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
    }*/

    @org.junit.jupiter.api.Test
    void updateTeacher() throws IOException, MissingIdException {
        TeacherRepository tr = new TeacherRepository("TeacherDataTest.json");
        CourseRepository cr = new CourseRepository(tr,"CourseDataTest.json");
        StudentRepository sr = new StudentRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        controller.updateTeacher("NewFirstName","NewLastName",1);
        for (Course c:controller.getCr().getAll()) {
            assertEquals(c.getTeacher().getFirstName(),"NewFirstName");
            assertEquals(c.getTeacher().getLastName(),"NewLastName");
        }
        try {
            controller.updateTeacher("test1", "test1", 2);
            assert(false);
        }
        catch (MissingIdException e){
            assert(true);
        }
    }

    @org.junit.jupiter.api.Test
    void updateStudent() throws IOException {
        TeacherRepository tr = new TeacherRepository("TeacherDataTest.json");
        CourseRepository cr = new CourseRepository(tr,"CourseDataTest.json");
        StudentRepository sr = new StudentRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        try {
            controller.updateStudent("NewFirstName","NewLastName",6,0);
            assert(false);
        }
        catch (MissingIdException e){
            assert(true);
        }
    }

    @org.junit.jupiter.api.Test
    void updateCourse() throws IOException {
        TeacherRepository tr = new TeacherRepository("TeacherDataTest.json");
        CourseRepository cr = new CourseRepository(tr,"CourseDataTest.json");
        StudentRepository sr = new StudentRepository(cr,"StudentDataTest.json");
        Controller controller = new Controller(cr,tr,sr);
        try {
            controller.updateCourse("NewName",1,10,40,6);
            assert(false);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(true);
        }

        try {
            controller.updateCourse("NewName",2,10,40,1);
            assert(false);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(true);
        }
        try {
            controller.updateCourse("NewName",1,-1,40,1);
            assert(false);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(true);
        }
        try {
            controller.updateCourse("NewName",1,10,40,1);
            assert(true);
        }
        catch (MissingIdException | MaxSizeException e){
            assert(false);
        }
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