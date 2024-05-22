package fr.ul.miage.genielogiciel.parking;
import java.util.Scanner;

public class Launcher {

    private static Scanner scanner;

    public static void main(String[] args) {

        // test of git

        scanner = new Scanner(System.in);
        System.out.println("Welcome to the FastBorne!");
        System.out.println("Do you have an account? (yes/no)");

        String hasAccount = scanner.nextLine().trim().toLowerCase();
        loginForm(hasAccount);

    }

    // we will update later.
    public static void loginForm(String hasAccount) {
        while (true) {
            if (hasAccount.equals("yes")) {
                System.out.println("");
            } else if (hasAccount.equals("no")) {
                System.out.println("Do you want to create one? (yes/no)");
                String createAccount = scanner.nextLine().trim().toLowerCase();
                registationForm(createAccount);
            } else {
                System.out.println("Error. You can choose only yes or no.");
                loginForm(hasAccount);
            }
        }
    }

    public static void registationForm(String createAccount) {
        while (true) {
            if (createAccount.equals("yes")) {
                System.out.println("registration form... (update later) ");
                System.out.println("Your account has been created!");
                break;
            } else if (createAccount.equals("no")) {
                //System.out.println("you choose no");
                System.out.println("You cannot use the application without account.");
                System.out.println("Bye!");
                break;

            } else {
                System.out.println("Error. You can choose only yes or no.");
                registationForm(createAccount);
            }
    }
    }


}
