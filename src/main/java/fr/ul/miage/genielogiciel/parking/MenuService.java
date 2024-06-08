package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class MenuService {

    private static final String LINE_OF_DASH = "-------------------------";

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

    public void mainMenu(Scanner input, ChargingStationList chargingStations, ClientList clients, ReservationService reservationService, reservationList reservations) {
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
                handleUserMenu(input, licenseNumber, clients, chargingStations, reservations);
                break;
            case 2:
                System.out.print("Please, enter your reservation number: ");
                String reservationNumber = input.nextLine();
                handleUserMenu(input, reservationNumber, clients, chargingStations, reservations);
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

    private void handleUserMenu(Scanner input, String identifier, ClientList clients, ChargingStationList chargingStations, reservationList reservations) {
        ReservationManager reservationManager = new ReservationManager();
        ReservationService reservationService = new ReservationService();

        Client client = clients.findClientByLicense(identifier);
        if (client == null) {
            client = clients.findClientByReservationNumber(identifier);
        }

        if (client == null) {
            System.out.println("Client not found.");
            return;
        }

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
                case 2 -> viewReservationStatus(input, client, chargingStations, reservationManager, reservations);
                case 3 -> viewAvailableStations(input, chargingStations, reservationService, clients, reservations);
                case 4 -> System.out.println("Signing out...  ---");
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (selection != 4);
    }

    private void viewReservationStatus(Scanner input, Client client, ChargingStationList chargingStations, ReservationManager reservationManager, reservationList reservations) {
        LocalDateTime currentTime = LocalDateTime.now();
        ChargingStation station = chargingStations.findChargingStationByClient(client);

        if (station == null) {
            System.out.println("No active reservation found.");
            return;
        }

        Reservation reservation = reservations.findReservationByClient(client);

        if (reservation == null) {
            System.out.println("No active reservation found.");
            return;
        }


        if (reservation.isConfirmed) {
            System.out.println("Reservation is currently active.");
            System.out.printf("Time remaining: %d minutes.%n", ChronoUnit.MINUTES.between(currentTime, reservation.endTime));
            System.out.println("1 - Check out");
            System.out.println("2 - Back to user menu");

            int choice = checkInputMenu(input, 2);
            if (choice == 1) {
                reservationManager.checkOut(station, client, currentTime);
            }
        } else {
            System.out.println("Reservation is not active.");
            System.out.println("1 - Check in");
            System.out.println("2 - Back to user menu");

            int choice = checkInputMenu(input, 2);
            if (choice == 1) {
                reservationManager.checkIn(station, client, currentTime);
            }
        }

    }

    private void viewAvailableStations(Scanner input, ChargingStationList chargingStations, ReservationService reservationService, ClientList clients, reservationList reservations) {
        List<ChargingStation> availableStations = chargingStations.findAvailableStations();

        if (!availableStations.isEmpty()) {
            System.out.println("Available stations:");
            for (ChargingStation station : availableStations) {
                System.out.println("ID: " + station.getIdStation());
            }

            System.out.println("Would you like to reserve a station? (yes/no)");
            String choice = input.nextLine().trim().toLowerCase();
            if (choice.equals("yes")) {
                reservationService.reserveChargingStation(input, chargingStations, clients, reservations);
            }
        } else {
            System.out.println("No available stations at the moment.");
        }
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
