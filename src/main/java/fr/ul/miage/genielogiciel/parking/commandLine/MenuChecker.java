package fr.ul.miage.genielogiciel.parking.commandLine;

import java.util.Scanner;

/**
 * Checker of the menu input
 */
public class MenuChecker {

    private final Displayer displayer;

    private final Scanner scanner;

    public MenuChecker(Scanner scanner, Displayer displayer){
        this.displayer = displayer;
        this.scanner = scanner;
    }

    /**
     * Check the input of the user
     * @param maxPointsMenu maximum point of the menu
     * @return number of the selection or -1
     */
    public int checkInputMenu(int maxPointsMenu) {
        int selection = -1;

        while (!(selection >= 1 && selection <= maxPointsMenu)) {
            displayer.displayMessage("Enter the number of your choice: ");
            if (scanner.hasNextInt()) {
                selection = scanner.nextInt();
                scanner.nextLine();
                if (!(selection >= 1 && selection <= maxPointsMenu)) {
                    displayer.displayErrorMessage("Invalid input. Please enter a number between 1 and " + maxPointsMenu);
                }
            } else {
                displayer.displayErrorMessage("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        return selection;
    }

}
