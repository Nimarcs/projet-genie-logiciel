package fr.ul.miage.genielogiciel.parking.commandLine;

import fr.ul.miage.genielogiciel.parking.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class MenuService {


    private final Displayer displayer;
    private final Scanner scanner;

    public MenuService (Scanner scanner ,Displayer displayer){
        this.scanner = scanner;
        this.displayer = displayer;
    }

    /**
     * Display the first menu
     * Displayed to a not connected user
     * @return selection made from the menu
     */
    public int welcomeMenu() {
        int selection;
        String[] options = new String[]{"Login", "Create Account", "Quit"};
        displayer.displayMenu(options, "WELCOME");

        selection = checkInputMenu(options.length);
        return selection;
    }

    /**
     * Display the main menu of someone connected
     * @param facadeInterface link to the datas
     * @param reservationService service to manage the reservations
     */
    public void mainMenu(FacadeInterface facadeInterface, ReservationService reservationService) {
        int selection;

        String[] options = new String[]{"Find with license number", "Find with reservation number", "I don't have the reservation", "Disconnect"};
        displayer.displayMenu(options, "MAIN MENU");

        selection = checkInputMenu(options.length);

        switch (selection) {
            case 1 -> handleWithLicenseNumber(facadeInterface, reservationService);
            case 2 -> handleWithReservationNumber(facadeInterface, reservationService);
            case 3 -> {
                displayer.displayMessage("Waiting...");
                reservationService.reserveChargingStation(facadeInterface);
            }
            case 4 -> {
                facadeInterface.setCurrentClient(null);
                displayer.displayImportantMessage("Disconnection sucessfull. Returning back");
            }
            default -> displayer.displayErrorMessage("Invalid choice. Please enter a number between 1 and 3.");
        }
    }

    /**
     * Handle the choice of the license number
     * @param facadeInterface link to the data
     * @param reservationService service to display reservation linked interface
     */
    private void handleWithLicenseNumber(FacadeInterface facadeInterface, ReservationService reservationService){
        System.out.print("Please, enter your license number: ");
        String licenseNumber = scanner.nextLine();
        Client client = facadeInterface.findClientByLicense(licenseNumber);

        if (client == null) {
            displayer.displayErrorMessage("Client not found.");
            return;
        }

        handleUserMenu(facadeInterface, reservationService, client);
    }

    /**
     * Handle the choice of the reservation number
     * @param facadeInterface link to the data
     * @param reservationService service to display reservation linked interfaces
     */
    private void handleWithReservationNumber(FacadeInterface facadeInterface, ReservationService reservationService){
        System.out.print("Please, enter your reservation number: ");
        String reservationNumber = scanner.nextLine();
        Client client = facadeInterface.findClientByReservationNumber(reservationNumber);

        if (client == null) {
            displayer.displayErrorMessage("Client not found.");
            return;
        }

        handleUserMenu(facadeInterface, reservationService, client);
    }

    /**
     * Handle the display of the menu linked to the user
     * @param facadeInterface link to the data
     * @param reservationService service to display reservation linked interfaces
     * @param client client of which we want to display the menu
     */
    private void handleUserMenu(FacadeInterface facadeInterface, ReservationService reservationService, Client client) {

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
                case 1 -> reservationService.reserveChargingStation(input, chargingStations, clients);
                case 2 -> viewReservationStatus(input, client, chargingStations, reservationManager, reservations);
                case 3 -> viewAvailableStations(input, chargingStations, reservationService, clients);
                case 4 -> System.out.println("Signing out...  ---");
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (selection != 4);
    }

    private void viewReservationStatus(Scanner input, Client client, ChargingStationList chargingStations, ReservationManager reservationManager, ReservationList reservations) {
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

    private void viewAvailableStations(Scanner input, ChargingStationList chargingStations, ReservationService reservationService, ClientList clients) {
        List<ChargingStation> availableStations = chargingStations.findAvailableStations();

        if (!availableStations.isEmpty()) {
            System.out.println("Available stations:");
            for (ChargingStation station : availableStations) {
                System.out.println("ID: " + station.getIdStation());
            }

            System.out.println("Would you like to reserve a station? (yes/no)");
            String choice = input.nextLine().trim().toLowerCase();
            if (choice.equals("yes")) {
                reservationService.reserveChargingStation(input, chargingStations, clients);
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
