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
        boolean successLogin;

        do {
            userChoice = menuService.welcomeMenu(scanner);

            switch (userChoice) {
                case 1 -> {
                    successLogin = clientService.loginForm(facadeInterface);
                    if (successLogin) {
                        menuService.mainMenu(facadeInterface, reservationService);
                    }
                }
                case 2 -> clientService.registrationForm(facadeInterface);
                case 3 -> System.out.println("--- Bye! ---");
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (userChoice != 3);

        scanner.close();
    }
}


