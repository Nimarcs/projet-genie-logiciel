package fr.ul.miage.genielogiciel.parking;
import javax.swing.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Scanner;

public class Launcher {

//    TO DO (*):
//    (1) log in form
//    (2) registration form


    private static Scanner scanner;
    private static ClientList clients = new ClientList();

    public static void main(String[] args) {

        // test of git

// again
        scanner = new Scanner(System.in);
        System.out.println("Welcome to the FastBorne!");
        int userChoice;
        boolean successLogin;
        Client client1 = new Client();

        do {
            userChoice = welcomeMenu(scanner);

            switch (userChoice) {
                case 1:
                    successLogin = loginForm(scanner, client1);
                    if (successLogin) {
                        mainMenu(scanner);
                    }
                    break;
                case 2:
                    registationForm(scanner, client1);
                    break;
                case 3:
                    System.out.println("--- Bye! ---");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (userChoice != 3);
        scanner.close();
    }

    public static int welcomeMenu(Scanner  input) {

        int selection = -1;

        System.out.println("\n-------------------------");
        System.out.println("          WELCOME         ");
        System.out.println("-------------------------");
        System.out.println("1 - Login");
        System.out.println("2 - Create Account");
        System.out.println("3 - Quit");

        System.out.print("Enter the number of your choice: ");


        while (true) {
            System.out.print("Enter the number of your choice: ");
            if (input.hasNextInt()) {
                selection = input.nextInt();
                if (selection >= 1 && selection <= 3) {
                    break; // Valid input, exit the loop
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.next(); // Clear the invalid input
            }
        }



        return selection;
    }



    // TO DO (1) - login form
    public static  boolean loginForm (Scanner  input, Client client) {
        System.out.println("\n-------------------------");
        System.out.println("        LOGIN FORM         ");
        System.out.println("-------------------------");

        boolean success = false;

        System.out.println("Enter your credentials;");

        System.out.print("Username: ");
        System.out.print("\nPassword: ");
        success = true;

        return success;

    }


    // TO DO (2) - registration form
    public static void registationForm(Scanner  input, Client client) {
        System.out.println("\n-------------------------");
        System.out.println("     REGISTRATION FORM     ");
        System.out.println("-------------------------");

        System.out.println("Enter your credentials");

        System.out.print("Username: ");
        System.out.print("\nPassword: ");
    }


    public static void mainMenu(Scanner input) {
        int selection = -1;

        System.out.println("\n-------------------------");
        System.out.println("         MAIN MENU         ");
        System.out.println("-------------------------");

        System.out.println("Please enter your immatriculation or reservation number.");
        System.out.println("1 - License number");
        System.out.println("2 - Reservation number");
        System.out.println("3 - Quit");

        while (true) {
            System.out.print("Enter the number of your choice: ");
            if (input.hasNextInt()) {
                selection = input.nextInt();
                input.nextLine();  // clear the newline character
                if (selection >= 1 && selection <= 3) {
                    break; // Valid input, exit the loop
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.next(); // Clear the invalid input
            }
        }

        switch (selection) {
            case 1:
                System.out.print("Please, enter your license number: ");
                String licenseNumber = input.nextLine();
                System.out.println("License number entered: " + licenseNumber);
                // go to the next method with this
                break;
            case 2:
                System.out.print("Please, enter your reservation number: ");
                String reservationNumber = input.nextLine();
                System.out.println("Reservation number entered: " + reservationNumber);
                // go to the next method with this
                break;
            case 3:
                System.out.println("--- Bye! ---");
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
        }
    }

}
