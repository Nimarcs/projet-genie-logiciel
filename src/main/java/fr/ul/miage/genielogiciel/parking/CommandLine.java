package fr.ul.miage.genielogiciel.parking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLine {


    public void run(Scanner scanner, ArrayList<ChargingStation> chargingStations, ArrayList<Client> clients, ArrayList<Reservation> reservations) {
        ClientService clientService = new ClientService();
        MenuService menuService = new MenuService();

        System.out.println("Welcome to the FastBorne!");
        int userChoice;
        boolean successLogin;



        do {
            userChoice = menuService.welcomeMenu(scanner);
            Client client = new Client();
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


