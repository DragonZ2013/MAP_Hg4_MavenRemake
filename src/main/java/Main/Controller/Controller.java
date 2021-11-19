package Main.Controller;

import Main.Exceptions.ExistentIdException;
import Main.Exceptions.MaxSizeException;
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
    TeacherRepository tr;
    CourseRepository cr;
    StudentRepository sr;

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

    public void updateStudent(String firstName,String lastName,int studentId,int totalCredits) throws MissingIdException {
        Student student = null;
        for(Student s: sr.getAll())
            if(s.getStudentId()==studentId)
                student = s;
        if(student==null)
            throw new MissingIdException("Student with given Id doesn't exist");
        Student s = new Student(firstName,lastName,studentId,totalCredits,student.getEnrolledCourses());
        sr.update(s);

    }

    public void updateCourse(String name,int teacherId,int maxEnrollment,int credits,int courseId) throws MissingIdException, MaxSizeException {
        Course course = null;
        for(Course c: cr.getAll())
            if(c.getCourseId()==courseId)
                course = c;
        if(course==null)
            throw new MissingIdException("Course with given Id doesn't exist");
        Teacher teacher = null;
        for(Teacher t:tr.getAll())
            if(t.getTeacherId()==teacherId)
                teacher=t;
        if(teacher==null)
            throw new MissingIdException("Teacher with given Id doesn't exist");
        if(maxEnrollment<course.getStudentsEnrolled().size())
            throw new MaxSizeException("Student array has more elements than new Max Enrollment");
        Teacher t = course.getTeacher();
        t.getCourses().removeIf(teach->teach.getCourseId()==courseId);
        tr.update(t);
        Course c = new Course(name,teacher,maxEnrollment,course.getStudentsEnrolled(),credits,courseId);
        teacher.getCourses().add(course);
        Teacher teacherRet = new Teacher(teacher.getFirstName(),teacher.getLastName(),teacher.getCourses(),teacherId);
        tr.update(teacherRet);
        cr.update(c);
    }

    public void deleteCourse(int courseId) throws MissingIdException {
        Course course = null;
        for(Course c: cr.getAll())
            if(c.getCourseId()==courseId)
                course = c;
        if(course==null)
            throw new MissingIdException("Course with given Id doesn't exist");
        for(Student s:course.getStudentsEnrolled()){
            s.getEnrolledCourses().removeIf(o->o.getCourseId()==courseId);
            sr.update(s);
        }
        cr.delete(course);
    }

    public void deleteTeacher(int teacherId) throws MissingIdException {
        Teacher teacher = null;
        for(Teacher t: tr.getAll())
            if(t.getTeacherId()==teacherId)
                teacher = t;
        if(teacher == null)
            throw new MissingIdException("Teacher with given Id doesn't exist");
        for(Course c:teacher.getCourses())
            this.deleteCourse(c.getCourseId());
        tr.delete(teacher);
    }

    public void deleteStudent(int studentId) throws MissingIdException {
        Student student = null;
        for(Student s: sr.getAll())
            if(s.getStudentId()==studentId)
                student = s;
        if(student==null)
            throw new MissingIdException("Student with given Id doesn't exist");
        for(Course c:student.getEnrolledCourses()){
            c.getStudentsEnrolled().removeIf(o->o.getStudentId()==studentId);
            cr.update(c);
        }

        sr.delete(student);
    }

    public void registerStudent(int studentId,int courseId) throws MissingIdException, MaxSizeException {
        Student student = null;
        for(Student s: sr.getAll())
            if(s.getStudentId()==studentId)
                student = s;
        if(student==null)
            throw new MissingIdException("Student with given Id doesn't exist");
        Course course = null;
        for(Course c: cr.getAll())
            if(c.getCourseId()==courseId)
                course = c;
        if(course==null)
            throw new MissingIdException("Course with given Id doesn't exist");
        if(course.getMaxEnrollment()==course.getStudentsEnrolled().size())
            throw new MaxSizeException("Course already hax maximum number of students enrolled");
        student.setTotalCredits(student.getTotalCredits()+course.getCredits());
        student.getEnrolledCourses().add(course);
        sr.update(student);
        course.getStudentsEnrolled().add(student);
        cr.update(course);



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
     * Returns the list of students with more than minCredits credits
     * @param minCredits
     * @return retList List<Student>
     */
    public List<Student> FilterStudents(int minCredits){
        List<Student> studentList = sr.getAll();
        List<Student> retList = studentList.stream().filter(o->o.getTotalCredits()>=minCredits).toList();

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
     * @param minCredits
     * @return retList List<Course>
     */
    public List<Course> FilterCourses(int minCredits){
        List<Course> courseList = cr.getAll();
        List<Course> retList = courseList.stream().filter(o->o.getCredits()>=minCredits).toList();


        return retList;
    }

}
