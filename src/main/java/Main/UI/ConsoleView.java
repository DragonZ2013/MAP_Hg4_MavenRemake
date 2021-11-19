package Main.UI;

import Main.Controller.Controller;
import Main.Exceptions.ExistentIdException;

import java.util.Scanner;

public class ConsoleView {
    private Controller controller;

    public ConsoleView(Controller controller) {
        this.controller = controller;
    }

    /**
     * Primary run function for Console Application - WIP: Exception Handling
     * @throws ExistentIdException
     */
    public void Run() throws ExistentIdException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.Create Teacher\n2.Update Teacher\n3.Delete Teacher\n4.Create Course\n5.Update Course\n6.Delete Course\n7.Create Student\n8.Update Student\n9.Delete Student\n10.Register Student\n11.Sort Course\n12.Filter Course\n13.Sort Students\n14.Filter Students\n15.Exit");
        String input =scanner.next();
        System.out.println(input);
        switch (input) {
            case "1":
                System.out.println("Give Teacher FirstName");
                String teacherFirstName =scanner.next();
                System.out.println("Give Teacher LastName");
                String teacherLastName =scanner.next();
                System.out.println("Give Teacher Id");
                int teacherId =Integer.parseInt(scanner.next());
                controller.createTeacher(teacherFirstName,teacherLastName,teacherId);
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                break;
            case "9":
                break;
            case "10":
                break;
            case "11":
                break;
            case "12":
                break;
            case "13":
                break;
            case "14":
                break;
            case "15":
                break;
            default:

        }
    }
}
