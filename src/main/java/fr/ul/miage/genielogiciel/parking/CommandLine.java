package fr.ul.miage.genielogiciel.parking;

import java.util.List;
import java.util.Scanner;

public class CommandLine {


    public void run(Scanner scanner, Client client, ClientList clients, ChargingStationList chargingStations, reservationList reservations) {
        ClientService clientService = new ClientService();
        MenuService menuService = new MenuService();
        ReservationService reservationService = new ReservationService();

        System.out.println("Welcome to the FastBorne!");
        int userChoice;
        boolean successLogin;

        do {
            userChoice = menuService.welcomeMenu(scanner);

            switch (userChoice) {
                case 1 -> {
                    successLogin = clientService.loginForm(scanner, clients);
                    if (successLogin) {
                        menuService.mainMenu(scanner, chargingStations, clients, reservationService, reservations);
                    }
                }
                case 2 -> clientService.registrationForm(scanner, client);
                case 3 -> System.out.println("--- Bye! ---");
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (userChoice != 3);

        scanner.close();
    }
}


