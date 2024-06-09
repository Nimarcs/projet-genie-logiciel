package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuService {

    private static final String LINE_OF_DASH = "-------------------------";
    private final ReservationService reservationService = new ReservationService();

    public int welcomeMenu(Scanner input) {
        int selection;

        System.out.println("\n" + LINE_OF_DASH);
        System.out.println("          WELCOME         ");
        System.out.println(LINE_OF_DASH);
        System.out.println("1 - Login");
        System.out.println("2 - Create Account");
        System.out.println("3 - Quit");

        selection = checkInputMenu(input, 3);
        return selection;
    }

    public void mainMenu(Scanner input, ArrayList<ChargingStation> chargingStations, ArrayList<Client> clients, ArrayList<Reservation> reservations) {
        int selection;

        System.out.println("\n" + LINE_OF_DASH);
        System.out.println("         MAIN MENU         ");
        System.out.println(LINE_OF_DASH);

        System.out.println("Please enter your license number or reservation number.");
        System.out.println("1 - License number");
        System.out.println("2 - Reservation number");
        System.out.println("3 - I don't have the reservation");
        System.out.println("4 - Back");

        selection = checkInputMenu(input, 4);

        switch (selection) {
            case 1:
                System.out.print("Please, enter your license number: ");
                String licenseNumber = input.nextLine();
                handleUserMenu(input, licenseNumber,  chargingStations, clients, reservations);
                break;
            case 2:
                System.out.print("Please, enter your reservation number: ");
                String reservationNumber = input.nextLine();
                handleUserMenu(input, reservationNumber, chargingStations, clients,  reservations);
                break;
            case 3:
                System.out.print("Waiting...");
                reservationService.reserveChargingStation(input, chargingStations, clients, reservations);
                break;
            case 4:
                System.out.println("Returning back...  ---");
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
        }
    }

    private void handleUserMenu(Scanner input, String identifier, ArrayList<ChargingStation> chargingStations, ArrayList<Client> clients, ArrayList<Reservation> reservations) {
        ReservationManager reservationManager = new ReservationManager();

        Optional<Client> clientOpt = clients.stream()
                .filter(client -> client.getPlateNumbers().equals(identifier) ||
                        String.valueOf(client.getReservationNumber()).equals(identifier))
                .findFirst();

        if (clientOpt.isEmpty()) {
            System.out.println("Client not found.");
            return;
        }

        Client client = clientOpt.get();

        int selection;
        do {
            System.out.println("\n" + LINE_OF_DASH);
            System.out.println("         USER MENU         ");
            System.out.println(LINE_OF_DASH);
            System.out.println("1 - Reserve a charging station");
            System.out.println("2 - Check reservation status");
            System.out.println("3 - View available stations");
            System.out.println("4 - Sign out");

            selection = checkInputMenu(input, 4);

            switch (selection) {
                case 1 -> reservationService.reserveChargingStation(input, chargingStations, clients, reservations);
                case 2 -> reservationService.viewReservationStatus(input, client, chargingStations, reservationManager, reservations);
                case 3 -> reservationService.viewAvailableStations(input, chargingStations, reservationService, clients, reservations);
                case 4 -> System.out.println("Signing out...  ---");
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (selection != 4);
    }

    private int checkInputMenu(Scanner input, int maxPointsMenu) {
        int selection = -1;

        while (true) {
            System.out.print("Enter the number of your choice: ");
            if (input.hasNextInt()) {
                selection = input.nextInt();
                input.nextLine();
                if (selection >= 1 && selection <= maxPointsMenu) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and " + maxPointsMenu);
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
        }

        return selection;
    }
}
