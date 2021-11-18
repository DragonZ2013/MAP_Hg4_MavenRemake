package Main.Controller;

import Main.Model.Course;
import Main.Model.Student;
import Main.Model.Teacher;
import Main.Repository.CourseRepository;
import Main.Repository.StudentRepository;
import Main.Repository.TeacherRepository;

import java.util.Comparator;
import java.util.List;

public class Controller {
    CourseRepository cr = new CourseRepository();
    TeacherRepository tr = new TeacherRepository();
    StudentRepository sr = new StudentRepository();

    public Controller(CourseRepository cr, TeacherRepository tr, StudentRepository sr) {
        this.cr = cr;
        this.tr = tr;
        this.sr = sr;
    }

    public CourseRepository getCr() {
        return cr;
    }

    public void setCr(CourseRepository cr) {
        this.cr = cr;
    }

    public TeacherRepository getTr() {
        return tr;
    }

    public void setTr(TeacherRepository tr) {
        this.tr = tr;
    }

    public StudentRepository getSr() {
        return sr;
    }

    public void setSr(StudentRepository sr) {
        this.sr = sr;
    }

    /**WIP - add id validation and exception
     *
     * @param s
     */
    public void addToStudentRepo(Student s){
        sr.create(s);
    }

    public void addToCourseRepo(Course c) {
        cr.create(c);
    }

    public  void addToTeacherRepo(Teacher t){
        tr.create(t);
    }

    /**
     * Returns the list of students sorted by their LastName - WIP - FirstName sorting, thenComparing breaks
     * @return retList List<Student>
     */
    public List<Student> SortStudents(){
        List<Student> studentList = sr.getAll();
        Comparator<Student> studentComparator = Comparator.comparing(o -> o.getLastName());//.thenComparing(o -> o.getFirstName());
        List<Student> retList = studentList.stream().sorted(studentComparator).toList();
        return retList;
    }

    /**
     * Returns the list of students with more than minCreds credits
     * @param minCreds
     * @return retList List<Student>
     */
    public List<Student> FilterStudents(int minCreds){
        List<Student> studentList = sr.getAll();
        List<Student> retList = studentList.stream().filter(o->o.getTotalCredits()>=minCreds).toList();

        return retList;
    }

    /**
     * Returns the list of courses sorted by their credits
     * @return retList List<Course>
     */
    public List<Course> SortCourses(){
        List<Course> courseList = cr.getAll();
        Comparator<Course> courseComparator = Comparator.comparing(o -> Integer.valueOf(o.getCredits()));
        List<Course> retList = courseList.stream().sorted(courseComparator).toList();


        return retList;
    }

    /**
     * Returns the list of courses with more than minCreds credits
     * @param minCreds
     * @return retList List<Course>
     */
    public List<Course> FilterCourses(int minCreds){
        List<Course> courseList = cr.getAll();
        List<Course> retList = courseList.stream().filter(o->o.getCredits()>=minCreds).toList();


        return retList;
    }

}
