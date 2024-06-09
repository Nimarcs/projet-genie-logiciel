package fr.ul.miage.genielogiciel.parking;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles the command line interface for the FastBorne application.
 * It manages the user interactions, including logging in, creating accounts, and navigating menus.
 */
public class CommandLine {

    /**
     * Runs the main loop of the FastBorne application.
     * Displays the welcome message and handles user choices to log in, register, or quit.
     *
     * @param scanner          the scanner object to read user input
     * @param chargingStations the list of charging stations
     * @param clients          the list of clients
     * @param reservations     the list of reservations
     */
    public void run(Scanner scanner, ArrayList<ChargingStation> chargingStations, ArrayList<Client> clients, ArrayList<Reservation> reservations) {
        ClientService clientService = new ClientService();
        MenuService menuService = new MenuService();

        System.out.println("Welcome to the FastBorne!");
        int userChoice;

        do {
            userChoice = menuService.welcomeMenu(scanner);
            Client client;
            switch (userChoice) {
                case 1 -> {
                    client = clientService.loginForm(scanner, clients);
                    if (client != null) {
                        menuService.mainMenu(scanner, client, chargingStations, clients, reservations);
                    }
                }
                case 2 -> clientService.registrationForm(scanner, clients);
                case 3 -> System.out.println("--- Bye! ---");
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (userChoice != 3);

        scanner.close();
    }
}
