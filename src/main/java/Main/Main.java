package Main;

import Main.Controller.Controller;
import Main.Model.Course;
import Main.Model.Student;
import Main.Model.Teacher;
import Main.Repository.CourseRepository;
import Main.Repository.StudentRepository;
import Main.Repository.TeacherRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /**
     * !!! REPOSITORIES MUST BE LOADED IN ORDER: TEACHER -> COURSE -> STUDENT
     */
    public static void main(String[] args) throws IOException {
	// write your code here
        Teacher t = new Teacher("fN","lN",new ArrayList(),1);
        /*
        Course c = new Course("name1",t,5,new ArrayList(),20,1);
        List tempList = new ArrayList();
        tempList.add(c);
        t.setCourses(tempList);
        System.out.println(t);
        System.out.println(c);
*/
        Course c1 = new Course("name1",t,5,new ArrayList(),20,1);
        Course c2 = new Course("name2",t,5,new ArrayList(),45,2);
        Course c3 = new Course("name3",t,5,new ArrayList(),10,3);
        Student s1 = new Student("name3","name3",1,20,new ArrayList());
        Student s2 = new Student("name1","name1",2,40,new ArrayList());
        Student s3 = new Student("name2","name2",3,15,new ArrayList());

        TeacherRepository tr = new TeacherRepository("TeacherData.json");
        System.out.println(tr.getAll());
        //tr.create(t);
        CourseRepository cr = new CourseRepository(tr,"CourseData.json");
        System.out.println(cr.getAll());
        /*cr.create(c1);
        cr.create(c2);
        cr.create(c3);*/
        StudentRepository sr = new StudentRepository(cr,"StudentData.json");
        System.out.println(sr.getAll());
        /*sr.create(s1);
        s1.getEnrolledCourses().add(c1);
        sr.update(s1);
        sr.create(s2);
        s2.getEnrolledCourses().add(c2);
        sr.update(s2);
        sr.create(s3);
        s3.getEnrolledCourses().add(c3);
        sr.update(s3);*/
        Controller cont = new Controller(cr,null,sr);
        System.out.println(cont.SortCourses());
        System.out.println(cont.SortStudents());
        System.out.println(cont.FilterCourses(25));
        System.out.println(cont.FilterStudents(25));
        sr.close("StudentData.json");
        tr.close("TeacherData.json");
        cr.close("CourseData.json");
    }
}
