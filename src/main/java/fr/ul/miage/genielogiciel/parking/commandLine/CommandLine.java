package fr.ul.miage.genielogiciel.parking.commandLine;

import fr.ul.miage.genielogiciel.parking.*;

import java.util.Scanner;

public class CommandLine {


    public void run(FacadeInterface facadeInterface) {
        //Init in/out
        Scanner scanner = new Scanner(System.in);
        Displayer displayer = new DisplayerSout();


        //Init display services
        ClientService clientService = new ClientService(scanner, displayer);
        MenuService menuService = new MenuService(scanner, displayer);
        ReservationService reservationService = new ReservationService(scanner, displayer);


        displayer.displayMessage("Welcome to the FastBorne!");
        int userChoice;

        do {
            userChoice = menuService.welcomeMenu();

            if (facadeInterface.isConnected()){
                menuService.mainMenu(facadeInterface, reservationService);
            } else {
                // Not login choices
                switch (userChoice) {
                    case 1 -> clientService.loginForm(facadeInterface);
                    case 2 -> clientService.registrationForm(facadeInterface);
                    case 3 -> displayer.displayImportantMessage("Bye!");
                    default -> displayer.displayErrorMessage("Invalid choice. Please enter a number between 1 and 3.");
                }

            }
        } while (userChoice != 3);

        scanner.close();
    }
}


