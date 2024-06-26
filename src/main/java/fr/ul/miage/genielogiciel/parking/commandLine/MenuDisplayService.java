package fr.ul.miage.genielogiciel.parking.commandLine;

import fr.ul.miage.genielogiciel.parking.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuDisplayService {


    private final Displayer displayer;
    private final Scanner scanner;
    private final MenuChecker menuChecker;

    public MenuDisplayService(Scanner scanner, Displayer displayer, MenuChecker menuChecker){
        this.scanner = scanner;
        this.displayer = displayer;
        this.menuChecker= menuChecker;
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

        selection = menuChecker.checkInputMenu(options.length);
        return selection;
    }

    /**
     * Display the main menu of someone connected
     * @param facadeInterface link to the datas
     * @param reservationDisplayService service to manage the reservations
     */
    public void mainMenu(FacadeInterface facadeInterface, ReservationDisplayService reservationDisplayService, ClientDisplayService clientDisplayService) {
        int selection;

        String[] options = new String[]{"Find with license number", "Find with reservation number", "I don't have the reservation", "Disconnect"};
        displayer.displayMenu(options, "MAIN MENU");

        selection = menuChecker.checkInputMenu(options.length);

        switch (selection) {
            case 1 -> handleWithLicenseNumber(facadeInterface, reservationDisplayService, clientDisplayService);
            case 2 -> handleWithReservationNumber(facadeInterface, reservationDisplayService, clientDisplayService);
            case 3 -> {
                displayer.displayMessage("Waiting...");
                reservationDisplayService.reserveChargingStation(facadeInterface, clientDisplayService);
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
     * @param reservationDisplayService service to display reservation linked interface
     */
    private void handleWithLicenseNumber(FacadeInterface facadeInterface, ReservationDisplayService reservationDisplayService, ClientDisplayService clientDisplayService){
        System.out.print("Please, enter your license number: ");
        String licenseNumber = scanner.nextLine();

        if (!licenseNumber.equalsIgnoreCase(facadeInterface.getCurrentClient().getPlateNumbers())) {
            displayer.displayErrorMessage("Invalid license number");
            return;
        }

        handleUserMenu(facadeInterface, reservationDisplayService, facadeInterface.getCurrentClient(), clientDisplayService);
    }

    /**
     * Handle the choice of the reservation number
     * @param facadeInterface link to the data
     * @param reservationDisplayService service to display reservation linked interfaces
     */
    private void handleWithReservationNumber(FacadeInterface facadeInterface, ReservationDisplayService reservationDisplayService, ClientDisplayService clientDisplayService){
        System.out.print("Please, enter your reservation number: ");
        String reservationNumber = scanner.nextLine();

        if (reservationNumber.equals(String.valueOf(facadeInterface.getCurrentClient().getReservationNumber()))) {
            displayer.displayErrorMessage("Invalid reservation number");
            return;
        }

        handleUserMenu(facadeInterface, reservationDisplayService, facadeInterface.getCurrentClient(), clientDisplayService);
    }

    /**
     * Handle the display of the menu linked to the user
     * @param facadeInterface link to the data
     * @param reservationDisplayService service to display reservation linked interfaces
     * @param client client of which we want to display the menu
     */
    private void handleUserMenu(FacadeInterface facadeInterface, ReservationDisplayService reservationDisplayService, Client client, ClientDisplayService clientDisplayService) {

        int selection;
        do {
            String[] options = new String[]{"Reserve a charging station", "Check reservation status", "View available stations", "Sign out"};
            displayer.displayMenu(options, "USER MENU");

            selection = menuChecker.checkInputMenu(options.length);

            switch (selection) {
                case 1 -> reservationDisplayService.reserveChargingStation(facadeInterface, clientDisplayService);
                case 2 -> viewReservationStatus(client, facadeInterface);
                case 3 -> viewAvailableStations(facadeInterface, reservationDisplayService, clientDisplayService);
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

        Optional<Reservation> optionalReservation = facadeInterface.findReservationByClient(client);

        if (optionalReservation.isEmpty()) {
            displayer.displayErrorMessage("No active reservation found.");
            return;
        }

        Reservation reservation = optionalReservation.get();
        LocalDateTime currentTime = LocalDateTime.now();

        if (reservation.isConfirmed()) {
            displayer.displayMessage("Reservation is currently active.");
            displayer.displayMessage(String.format("Time remaining: %d minutes.%n", ChronoUnit.MINUTES.between(currentTime, reservation.getEndTime())));
            String[] options = new String[]{"Check out", "Back to user menu"};
            displayer.displayMenu(options, "ACTIVE RESERVATION");

            int choice = menuChecker.checkInputMenu(options.length);
            if (choice == 1) {
                facadeInterface.checkOutFromReservation(reservation);
            }
        } else {
            String[] options = new String[]{"Check in", "Back to user menu"};
            displayer.displayMenu(options, "NO ACTIVE RESERVATION");

            int choice = menuChecker.checkInputMenu(options.length);
            if (choice == 1) {
                facadeInterface.checkInFromReservation(reservation);
            }
        }

    }

    /**
     * Display the availible Charging station
     * @param facadeInterface link to the data
     * @param reservationDisplayService display service of the reservation
     */
    private void viewAvailableStations(FacadeInterface facadeInterface, ReservationDisplayService reservationDisplayService, ClientDisplayService clientDisplayService) {
        Collection<ChargingStation> availableStations = facadeInterface.findAvailableStations();

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
                        reservationDisplayService.reserveChargingStation(facadeInterface, clientDisplayService);
                    }
                    case "no", "n" -> choice = "no";
                    default -> displayer.displayErrorMessage("Entry invalid (yes/no expected). Try again :");
                }
            }
        } else {
            displayer.displayMessage("No available stations at the moment.");
        }
    }
}
