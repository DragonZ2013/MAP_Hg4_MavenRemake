package Main.UI;

import Main.Controller.Controller;
import Main.Exceptions.ExistentIdException;
import Main.Exceptions.MaxSizeException;
import Main.Exceptions.MissingIdException;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleView {
    private Controller controller;

    public ConsoleView(Controller controller) {
        this.controller = controller;
    }

    /**
     * Mainloop function for Console Application - WIP: Exception Handling
     * @throws ExistentIdException
     */
    public void Run() throws ExistentIdException, MissingIdException, MaxSizeException, IOException {
        Scanner scanner = new Scanner(System.in);
        String teacherFirstName,teacherLastName,courseName,studentFirstName,studentLastName;
        int teacherId,courseId,studentId,filterParam,continueLoop=1,courseTeacherId,courseMaxEnrollment,courseCredits,studentTotalCredits;
        while(continueLoop==1){
            System.out.println("1.Create Teacher\n2.Update Teacher\n3.Delete Teacher\n4.Create Course\n5.Update Course\n6.Delete Course\n7.Create Student\n8.Update Student\n9.Delete Student\n10.Register Student\n11.Sort Course\n12.Filter Course\n13.Sort Students\n14.Filter Students\n15.Exit");
            String input =scanner.next();
            System.out.println(input);
            switch (input) {
                case "1":
                    System.out.println("Give Teacher First Name");
                    teacherFirstName = scanner.next();
                    System.out.println("Give Teacher Last Name");
                    teacherLastName = scanner.next();
                    System.out.println("Give Teacher Id");
                    teacherId = Integer.parseInt(scanner.next());
                    controller.createTeacher(teacherFirstName, teacherLastName, teacherId);
                    break;
                case "2":
                    System.out.println("Give Teacher First Name");
                    teacherFirstName = scanner.next();
                    System.out.println("Give Teacher Last Name");
                    teacherLastName = scanner.next();
                    System.out.println("Give Teacher Id");
                    teacherId = Integer.parseInt(scanner.next());
                    controller.updateTeacher(teacherFirstName, teacherLastName, teacherId);
                    break;
                case "3":
                    System.out.println("Give Teacher Id");
                    teacherId = Integer.parseInt(scanner.next());
                    controller.deleteTeacher(teacherId);
                    break;
                case "4":
                    System.out.println("Give Course Name");
                    courseName=scanner.next();
                    System.out.println("Give Teacher Id");
                    courseTeacherId=Integer.parseInt(scanner.next());
                    System.out.println("Give Maximum Enrollment Spots");
                    courseMaxEnrollment=Integer.parseInt(scanner.next());
                    System.out.println("Give Credits");
                    courseCredits=Integer.parseInt(scanner.next());
                    System.out.println("Give Course Id");
                    courseId=Integer.parseInt(scanner.next());
                    controller.createCourse(courseName,courseTeacherId,courseMaxEnrollment,courseCredits,courseId);
                    break;
                case "5":
                    System.out.println("Give Course Name");
                    courseName=scanner.next();
                    System.out.println("Give Teacher Id");
                    courseTeacherId=Integer.parseInt(scanner.next());
                    System.out.println("Give Maximum Enrollment Spots");
                    courseMaxEnrollment=Integer.parseInt(scanner.next());
                    System.out.println("Give Credits");
                    courseCredits=Integer.parseInt(scanner.next());
                    System.out.println("Give Course Id");
                    courseId=Integer.parseInt(scanner.next());
                    controller.updateCourse(courseName,courseTeacherId,courseMaxEnrollment,courseCredits,courseId);
                    break;
                case "6":
                    System.out.println("Give Course Id");
                    courseId = Integer.parseInt(scanner.next());
                    controller.deleteCourse(courseId);
                    break;
                case "7":
                    System.out.println("Give Student First Name");
                    studentFirstName= scanner.next();
                    System.out.println("Give Student Last Name");
                    studentLastName= scanner.next();
                    System.out.println("Give Student Id");
                    studentId=Integer.parseInt(scanner.next());
                    System.out.println("Give Student Max Credits");
                    studentTotalCredits=Integer.parseInt(scanner.next());
                    controller.createStudent(studentFirstName,studentLastName,studentId,studentTotalCredits);
                    break;
                case "8":
                    System.out.println("Give Student First Name");
                    studentFirstName= scanner.next();
                    System.out.println("Give Student Last Name");
                    studentLastName= scanner.next();
                    System.out.println("Give Student Id");
                    studentId=Integer.parseInt(scanner.next());
                    System.out.println("Give Student Max Credits");
                    studentTotalCredits=Integer.parseInt(scanner.next());
                    controller.updateStudent(studentFirstName,studentLastName,studentId,studentTotalCredits);
                    break;
                case "9":
                    System.out.println("Give Student Id");
                    studentId=Integer.parseInt(scanner.next());
                    controller.deleteStudent(studentId);
                    break;
                case "10":
                    System.out.println("Give Student Id");
                    studentId=Integer.parseInt(scanner.next());
                    System.out.println("Give Course Id");
                    courseId=Integer.parseInt(scanner.next());
                    controller.registerStudent(studentId,courseId);
                    break;
                case "11":
                    System.out.println(controller.SortCourses());
                    break;
                case "12":
                    System.out.println("Give Filter Parameter");
                    filterParam=Integer.parseInt(scanner.next());
                    System.out.println(controller.FilterCourses(filterParam));
                    break;
                case "13":
                    System.out.println(controller.SortStudents());
                    break;
                case "14":
                    System.out.println("Give Filter Parameter");
                    filterParam=Integer.parseInt(scanner.next());
                    System.out.println(controller.FilterStudents(filterParam));
                    break;
                case "15":
                    continueLoop=0;
                    break;
                default:
            }
            controller.SaveAll();
        }
    }
}
