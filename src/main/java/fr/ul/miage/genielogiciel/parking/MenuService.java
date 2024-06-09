package fr.ul.miage.genielogiciel.parking;

import java.util.*;

/**
 * This service class provides functionalities for displaying menus
 * and handling user interactions in the FastBorne application.
 */
public class MenuService {

    private static final String LINE_OF_DASH = "-------------------------";
    private final ReservationService reservationService = new ReservationService();

    /**
     * Displays the welcome menu and returns the user's selection.
     *
     * @param input the scanner object to read user input
     * @return the user's menu selection
     */
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

    /**
     * Displays the main menu and handles user interactions based on their selection.
     *
     * @param input the scanner object to read user input
     * @param client the client object representing the logged-in user
     * @param chargingStations the list of charging stations
     * @param clients the list of clients
     * @param reservations the list of reservations
     */
    public void mainMenu(Scanner input, Client client, ArrayList<ChargingStation> chargingStations, ArrayList<Client> clients, ArrayList<Reservation> reservations) {
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
        System.out.println();
        switch (selection) {
            case 1:
                System.out.print("Please, enter your license number: ");
                String licenseNumber = input.nextLine();

                if (Objects.equals(client.getPlateNumbers(), licenseNumber)) {
                    handleUserMenu(input, client, chargingStations, clients, reservations);
                } else {
                    System.out.println("Invalid license number");
                }
                break;
            case 2:
                System.out.print("Please, enter your reservation number: ");
                String reservationNumber = input.nextLine();
                if (Objects.equals(client.getReservationNumber(), reservationNumber)) {
                    handleUserMenu(input, client, chargingStations, clients, reservations);
                } else {
                    System.out.println("Invalid reservation number");
                }
                break;
            case 3:
                System.out.print("Waiting...");
                reservationService.reserveChargingStation(input, client, chargingStations, clients, reservations);
                break;
            case 4:
                System.out.println("Returning back...  ---");
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
        }
    }

    /**
     * Handles the user menu and manages interactions for reserving charging stations,
     * checking reservation status, viewing available stations, and signing out.
     *
     * @param input the scanner object to read user input
     * @param client the client object representing the logged-in user
     * @param chargingStations the list of charging stations
     * @param clients the list of clients
     * @param reservations the list of reservations
     */
    private void handleUserMenu(Scanner input, Client client, ArrayList<ChargingStation> chargingStations, ArrayList<Client> clients, ArrayList<Reservation> reservations) {
        ReservationManager reservationManager = new ReservationManager();

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
                case 1 -> reservationService.reserveChargingStation(input, client, chargingStations, clients, reservations);
                case 2 -> reservationService.viewReservationStatus(input, client, chargingStations, reservationManager, reservations);
                case 3 -> reservationService.viewAvailableStations(input, client, chargingStations, reservationService, clients, reservations);
                case 4 -> System.out.println("Signing out...  ---");
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (selection != 4);
    }

    /**
     * Checks the user input for menu selections and ensures it is a valid number within the specified range.
     *
     * @param input the scanner object to read user input
     * @param maxPointsMenu the maximum number of menu points
     * @return the validated menu selection
     */
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
