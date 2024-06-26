package fr.ul.miage.genielogiciel.parking.commandLine;

import fr.ul.miage.genielogiciel.parking.*;

import java.util.Scanner;

public class CommandLine {

    private Scanner scanner;
    private Displayer displayer;

    public CommandLine(Scanner scanner, Displayer displayer){
        this.scanner = scanner;
        this.displayer = displayer;
    }

    public CommandLine(){
        this(new Scanner(System.in), new DisplayerSout());
    }

    public void run(FacadeInterface facadeInterface) {
        //Init in/out

        //Init checker
        MenuChecker menuChecker = new MenuChecker(scanner, displayer);

        //Init display services
        ClientDisplayService clientDisplayService = new ClientDisplayService(scanner, displayer);
        MenuDisplayService menuService = new MenuDisplayService(scanner, displayer, menuChecker);
        ReservationDisplayService reservationDisplayService = new ReservationDisplayService(scanner, displayer, menuChecker);


        displayer.displayMessage("Welcome to the FastBorne!");
        int userChoice = -1;

        do {
            if (facadeInterface.isConnected()){
                menuService.mainMenu(facadeInterface, reservationDisplayService, clientDisplayService);
            } else {
                userChoice = menuService.welcomeMenu();
                // Not login choices
                switch (userChoice) {
                    case 1 -> clientDisplayService.loginForm(facadeInterface);
                    case 2 -> clientDisplayService.registrationForm(facadeInterface);
                    case 3 -> displayer.displayImportantMessage("Bye!");
                    default -> displayer.displayErrorMessage("Invalid choice. Please enter a number between 1 and 3.");
                }

            }
        } while (userChoice != 3);

        scanner.close();
    }
}


