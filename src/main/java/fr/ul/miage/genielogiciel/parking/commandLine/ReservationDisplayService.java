package fr.ul.miage.genielogiciel.parking.commandLine;

import fr.ul.miage.genielogiciel.parking.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ReservationDisplayService {

    private final Displayer displayer;
    private final Scanner scanner;
    private final MenuChecker menuChecker;

    public ReservationDisplayService(Scanner scanner, Displayer displayer, MenuChecker menuChecker){
        this.scanner = scanner;
        this.displayer = displayer;
        this.menuChecker = menuChecker;
    }

    /**
     * Create a reservation for a charging station
     * @param facadeInterface link to the data
     */
    public void reserveChargingStation(FacadeInterface facadeInterface, ClientDisplayService clientDisplayService) {
        List<ChargingStation> availableStations = facadeInterface.findAvailableStations();

        if (availableStations.isEmpty()) {
            displayer.displayErrorMessage("No available stations at the moment. Please try again later.");
            return;
        }

        displayer.displayMessage("Available station found: ");

        int i = 1;
        int selection;

        for (ChargingStation station : availableStations) {
            displayer.displayMessage(i + ". ID: " + station.getIdStation());
            i++;
        }

        displayer.displayMessage("Please enter the ID of the station you want to reserve: ");
        selection = scanner.nextInt();
        scanner.nextLine(); // clear the newline character

        ChargingStation selectedStation = facadeInterface.findChargingStationById(selection);

        if (!(selectedStation != null && selectedStation.getDisponible())) {
            displayer.displayErrorMessage("Selected station is not available.");
            return;
        }
            displayer.displayMessage("Please enter your license number: ");
            String licenseNumber = scanner.nextLine();

            Client client = facadeInterface.findClientByLicense(licenseNumber);

            if (client != null) {
                manageClientAndReserve(facadeInterface,selectedStation, client);
            } else {
                findClientAndReserve(facadeInterface, selectedStation, clientDisplayService);
            }
    }

    /**
     * Display the menu to handle late Arrival
     * @param facadeInterface link to the data
     */
    public void handleLateArrival(FacadeInterface facadeInterface) {
        displayer.displayMessage("Please enter your license number: ");
        String licenseNumber = scanner.nextLine();

        Client client = facadeInterface.findClientByLicense(licenseNumber);

        if (client != null) {
            Reservation reservation = facadeInterface.findReservationByClient(client);
            if (reservation != null) {
                //TODO
                facadeInterface.askExtention(reservation);
            } else {
                displayer.displayErrorMessage("No active reservation found for this client.");
            }
        } else {
            displayer.displayErrorMessage("Client not found.");
        }
    }

    //TODO make so we can reach this point without an account
    /**
     * Find the client and reserve the charging station
     * @param facadeInterface link to the data
     * @param selectedStation charging station selected by the user
     */
    private void findClientAndReserve(FacadeInterface facadeInterface, ChargingStation selectedStation, ClientDisplayService clientDisplayService) {
        displayer.displayMessage("License number not recognized. Please enter your mobile number: ");
        String mobileNumber = scanner.nextLine();

        Client client = facadeInterface.findByMobilePhone(mobileNumber);

        if (client != null) {
            displayer.displayMessage("Please enter the expected charging duration (in hours): ");
            int duration = scanner.nextInt();
            //TODO manage miss input

            displayer.displayMessage("Temporary reservation made for mobile number " + mobileNumber + " with duration " + duration + " hours.");
            Reservation reservation = new Reservation(client, LocalDateTime.now(), LocalDateTime.now().plusHours(duration));
            facadeInterface.addReservation(selectedStation, reservation);
        } else {
            displayer.displayErrorMessage("We didn't find the account associated with this number.\n Please create a new one: ");
            clientDisplayService.registrationForm(facadeInterface);
        }
    }

    /**
     * Manage a client that want to reserve a charging station
     * @param selectedStation charging station selected by the user
     * @param client current client
     */
    private void manageClientAndReserve(FacadeInterface facadeInterface, ChargingStation selectedStation, Client client) {
        int selection;
        LocalDateTime endTime;
        String[] options = new String[]{"Expected duration", "Departure time"};
        displayer.displayMenu(options, "CHOOSE INPUT");

        selection = menuChecker.checkInputMenu(options.length);

        switch (selection) {
            case 1 -> {
                displayer.displayMessage("Enter expected duration: ");
                int duration = scanner.nextInt();
                endTime = LocalDateTime.now().plusHours(duration);
            }
            case 2 -> {
                displayer.displayMessage("Enter departure time: ");
                int hour = scanner.nextInt();
                endTime = LocalDateTime.now().withHour(hour);
            }
            default -> endTime = LocalDateTime.now().plusHours(1);
        }

        Reservation reservation = new Reservation(client, LocalDateTime.now(), endTime);
        facadeInterface.addReservation(selectedStation, reservation);
    }

}
