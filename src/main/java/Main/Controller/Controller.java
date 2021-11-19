package Main.Controller;

import Main.Exceptions.ExistentIdException;
import Main.Exceptions.MissingIdException;
import Main.Model.Course;
import Main.Model.Student;
import Main.Model.Teacher;
import Main.Repository.CourseRepository;
import Main.Repository.StudentRepository;
import Main.Repository.TeacherRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Controller {
    TeacherRepository tr = new TeacherRepository();
    CourseRepository cr = new CourseRepository(tr);
    StudentRepository sr = new StudentRepository(cr);

    public Controller(CourseRepository cr, TeacherRepository tr, StudentRepository sr) throws IOException {
        this.cr = cr;
        this.tr = tr;
        this.sr = sr;
    }

    public void createTeacher(String firstName,String lastName,int teacherId) throws ExistentIdException {
        for(Teacher t: tr.getAll())
            if(t.getTeacherId()==teacherId)
                throw new ExistentIdException("Teacher Id is already in array");
        Teacher t = new Teacher(firstName,lastName,new ArrayList<>(),teacherId);
        tr.create(t);
    }

    public void createStudent(String firstName,String lastName,int studentId,int totalCredits) throws ExistentIdException {
        for(Student s: sr.getAll())
            if(s.getStudentId()==studentId)
                throw new ExistentIdException("Student Id is already in array");
        Student s = new Student(firstName,lastName,studentId,totalCredits,new ArrayList<>());
        sr.create(s);

    }

    public void createCourse(String name,int teacherId,int maxEnrollment,int credits,int courseId) throws ExistentIdException, MissingIdException {
        for(Course c: cr.getAll())
            if(c.getCourseId()==courseId)
                throw new ExistentIdException("Course Id is already in array");
        Teacher teacher = null;
        for(Teacher t:tr.getAll())
            if(t.getTeacherId()==teacherId)
                teacher=t;
        if(teacher==null)
            throw new MissingIdException("Teacher with given Id doesn't exist");

        Course c = new Course(name,teacher,maxEnrollment,new ArrayList<>(),credits,courseId);

        cr.create(c);
        teacher.getCourses().add(c);
        tr.update(teacher);
    }


    public void updateTeacher(String firstName,String lastName,int teacherId) throws MissingIdException {
        Teacher teacher = null;
        for(Teacher t: tr.getAll())
            if(t.getTeacherId()==teacherId)
                teacher = t;
        if(teacher == null)
            throw new MissingIdException("Teacher with given Id doesn't exist");
        Teacher t = new Teacher(firstName,lastName,teacher.getCourses(),teacherId);
        tr.update(t);
    }

    public void updateStudent(String firstName,String lastName,int studentId,int totalCredits) throws ExistentIdException {
        Student student = null;
        for(Student s: sr.getAll())
            if(s.getStudentId()==studentId)
                student = s;
        assert student != null;
        Student s = new Student(firstName,lastName,studentId,totalCredits,student.getEnrolledCourses());
        sr.update(s);

    }

    public void updateCourse(String name,int teacherId,int maxEnrollment,int credits,int courseId) throws ExistentIdException, MissingIdException {
        Course course = null;
        for(Course c: cr.getAll())
            if(c.getCourseId()==courseId)
                course = c;
        Teacher teacher = null;
        for(Teacher t:tr.getAll())
            if(t.getTeacherId()==teacherId)
                teacher=t;
        if(teacher==null)
            throw new MissingIdException("Teacher with given Id doesn't exist");
        Teacher t = course.getTeacher();
        t.getCourses().removeIf(teach->teach.getCourseId()==courseId);
        tr.update(t);
        Course c = new Course(name,teacher,maxEnrollment,new ArrayList<>(),credits,courseId);
        teacher.getCourses().add(course);
        Teacher teacherRet = new Teacher(teacher.getFirstName(),teacher.getLastName(),teacher.getCourses(),teacherId);
        tr.update(teacherRet);
        cr.update(c);
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
