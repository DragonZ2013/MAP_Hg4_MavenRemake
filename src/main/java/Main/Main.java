package Main;

import Main.Controller.Controller;
import Main.Model.Course;
import Main.Model.Student;
import Main.Model.Teacher;
import Main.Repository.CourseRepository;
import Main.Repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
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
        Course c2 = new Course("name2",t,5,new ArrayList(),45,1);
        Course c3 = new Course("name3",t,5,new ArrayList(),10,1);
        Student s1 = new Student("name3","name3",1,20,new ArrayList());
        Student s2 = new Student("name1","name1",2,40,new ArrayList());
        Student s3 = new Student("name2","name2",3,15,new ArrayList());

        CourseRepository cr = new CourseRepository();
        cr.create(c1);
        cr.create(c2);
        cr.create(c3);
        StudentRepository sr = new StudentRepository();
        sr.create(s1);
        sr.create(s2);
        sr.create(s3);
        Controller cont = new Controller(cr,null,sr);
        System.out.println(cont.SortCourses());
        System.out.println(cont.SortStudents());
        System.out.println(cont.FilterCourses(25));
        System.out.println(cont.FilterStudents(25));
    }
}
