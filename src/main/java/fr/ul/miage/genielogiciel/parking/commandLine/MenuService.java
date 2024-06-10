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
    public void mainMenu(FacadeInterface facadeInterface, ReservationService reservationService, ClientService clientService) {
        int selection;

        String[] options = new String[]{"Find with license number", "Find with reservation number", "I don't have the reservation", "Disconnect"};
        displayer.displayMenu(options, "MAIN MENU");

        selection = checkInputMenu(options.length);

        switch (selection) {
            case 1 -> handleWithLicenseNumber(facadeInterface, reservationService, clientService);
            case 2 -> handleWithReservationNumber(facadeInterface, reservationService, clientService);
            case 3 -> {
                displayer.displayMessage("Waiting...");
                reservationService.reserveChargingStation(facadeInterface, clientService);
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
    private void handleWithLicenseNumber(FacadeInterface facadeInterface, ReservationService reservationService, ClientService clientService){
        System.out.print("Please, enter your license number: ");
        String licenseNumber = scanner.nextLine();
        Client client = facadeInterface.findClientByLicense(licenseNumber);

        if (client == null) {
            displayer.displayErrorMessage("Client not found.");
            return;
        }

        handleUserMenu(facadeInterface, reservationService, client, clientService);
    }

    /**
     * Handle the choice of the reservation number
     * @param facadeInterface link to the data
     * @param reservationService service to display reservation linked interfaces
     */
    private void handleWithReservationNumber(FacadeInterface facadeInterface, ReservationService reservationService, ClientService clientService){
        System.out.print("Please, enter your reservation number: ");
        String reservationNumber = scanner.nextLine();
        Client client = facadeInterface.findClientByReservationNumber(reservationNumber);

        if (client == null) {
            displayer.displayErrorMessage("Client not found.");
            return;
        }

        handleUserMenu(facadeInterface, reservationService, client, clientService);
    }

    /**
     * Handle the display of the menu linked to the user
     * @param facadeInterface link to the data
     * @param reservationService service to display reservation linked interfaces
     * @param client client of which we want to display the menu
     */
    private void handleUserMenu(FacadeInterface facadeInterface, ReservationService reservationService, Client client, ClientService clientService) {

        int selection;
        do {
            String[] options = new String[]{"Reserve a charging station", "Check reservation status", "View available stations", "Sign out"};
            displayer.displayMenu(options, "USER MENU");

            selection = checkInputMenu(options.length);

            switch (selection) {
                case 1 -> reservationService.reserveChargingStation(facadeInterface, clientService);
                case 2 -> viewReservationStatus(client, facadeInterface);
                case 3 -> viewAvailableStations(facadeInterface, reservationService, clientService);
                case 4 -> displayer.displayImportantMessage("Going back");
                default -> displayer.displayErrorMessage("Invalid choice. Please enter a number between 1 and 4.");
            }
        } while (selection != 4);
    }

    /**
     * Display the reservation status
     * @param client client that made the connection
     * @param facadeInterface link to the data
     */
    private void viewReservationStatus(Client client, FacadeInterface facadeInterface) {
        ChargingStation station = facadeInterface.findChargingStationByClient(client);

        if (station == null) {
            displayer.displayErrorMessage("No active reservation found.");
            return;
        }

        Reservation reservation = facadeInterface.findReservationByClient(client);

        if (reservation == null) {
            displayer.displayErrorMessage("No active reservation found.");
            return;
        }


        LocalDateTime currentTime = LocalDateTime.now();

        if (reservation.isConfirmed) {
            displayer.displayMessage("Reservation is currently active.");
            displayer.displayMessage(String.format("Time remaining: %d minutes.%n", ChronoUnit.MINUTES.between(currentTime, reservation.endTime)));
            String[] options = new String[]{"Check out", "Back to user menu"};
            displayer.displayMenu(options, "ACTIVE RESERVATION");

            int choice = checkInputMenu(options.length);
            if (choice == 1) {
                facadeInterface.checkOutFromReservation(reservation);
            }
        } else {
            String[] options = new String[]{"Check in", "Back to user menu"};
            displayer.displayMenu(options, "NO ACTIVE RESERVATION");

            int choice = checkInputMenu(options.length);
            if (choice == 1) {
                facadeInterface.checkInFromReservation(reservation);
            }
        }

    }

    /**
     * Display the availible Charging station
     * @param facadeInterface link to the data
     * @param reservationService display service of the reservation
     */
    private void viewAvailableStations(FacadeInterface facadeInterface, ReservationService reservationService, ClientService clientService) {
        List<ChargingStation> availableStations = facadeInterface.findAvailableStations();

        if (!availableStations.isEmpty()) {
            displayer.displayMessage("Available stations:");
            for (ChargingStation station : availableStations) {
                displayer.displayMessage("ID: " + station.getIdStation());
            }

            displayer.displayMessage("Would you like to reserve a station? (yes/no)");
            String choice = "";

            //While the solution is invalid
            while (!choice.equals("yes") && !choice.equals("no")) {
                choice = scanner.nextLine().trim().toLowerCase();
                switch (choice) {
                    case "yes", "y" -> {
                        choice = "yes";
                        reservationService.reserveChargingStation(facadeInterface, clientService);
                    }
                    case "no", "n" -> choice = "no";
                    default -> displayer.displayErrorMessage("Entry invalid (yes/no expected). Try again :");
                }
            }
        } else {
            displayer.displayMessage("No available stations at the moment.");
        }
    }

    /**
     * Check the input of the user
     * @param maxPointsMenu maximum point of the menu
     * @return number of the selection or -1
     */
    private int checkInputMenu(int maxPointsMenu) {
        int selection = -1;

        while (!(selection >= 1 && selection <= maxPointsMenu)) {
            displayer.displayMessage("Enter the number of your choice: ");
            if (scanner.hasNextInt()) {
                selection = scanner.nextInt();
                scanner.nextLine();
                if (!(selection >= 1 && selection <= maxPointsMenu)) {
                   displayer.displayErrorMessage("Invalid input. Please enter a number between 1 and " + maxPointsMenu);
                }
            } else {
                displayer.displayErrorMessage("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        return selection;
    }
}
