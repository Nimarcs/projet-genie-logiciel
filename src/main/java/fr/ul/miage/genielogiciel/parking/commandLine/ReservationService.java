package fr.ul.miage.genielogiciel.parking.commandLine;

import fr.ul.miage.genielogiciel.parking.*;
import fr.ul.miage.genielogiciel.parking.commandLine.CommandLine;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ReservationService {

    private final Displayer displayer;
    private final Scanner scanner;

    public ReservationService(Scanner scanner, Displayer displayer){
        this.scanner = scanner;
        this.displayer = displayer;
    }

    /**
     * Create a reservation for a charging station
     * @param facadeInterface link to the data
     */
    public void reserveChargingStation(FacadeInterface facadeInterface) {
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
                manageClientAndReserve(selectedStation, client);
            } else {
                findClientAndReserve(facadeInterface, selectedStation);
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

    /**
     * Find the client and reserve the charging station
     * @param input input of the user
     * @param chargingStations list of all the charging stations
     * @param clients list of all the clients
     * @param selectedStation charging station selected by the user
     */
    private void findClientAndReserve(Scanner input, ChargingStationList chargingStations, ClientList clients, ChargingStation selectedStation) {
        System.out.print("License number not recognized. Please enter your mobile number: ");
        String mobileNumber = input.nextLine();

        Client client2 = clients.findByMobilePhone(mobileNumber);

        if (client2 != null) {
            System.out.print("Please enter the expected charging duration (in hours): ");
            int duration = input.nextInt();

            System.out.println("Temporary reservation made for mobile number " + mobileNumber + " with duration " + duration + " hours.");
            Reservation reservation = new Reservation(client2, LocalDateTime.now(), LocalDateTime.now().plusHours(duration));
            reservationManager.addReservation(selectedStation, reservation);
        } else {
            System.out.println("We didn't find the account associated with this number. Please create a new one: ");
            new CommandLine().run(input, null, clients, chargingStations);
        }
    }

    /**
     * Manage a client that want to reserve a charging station
     * @param input input of the user
     * @param selectedStation charging station selected by the user
     * @param client current client
     */
    private void manageClientAndReserve(Scanner input, ChargingStation selectedStation, Client client) {
        int selection;
        LocalDateTime endTime;
        System.out.println("Choose what to enter: ");
        System.out.println("1. Expected duration ");
        System.out.println("2. Departure time ");

        selection = checkInputMenu(input, 3);

        switch (selection) {
            case 1 -> {
                System.out.println("Enter expected duration: ");
                int duration = input.nextInt();
                endTime = LocalDateTime.now().plusHours(duration);
            }
            case 2 -> {
                System.out.println("Enter departure time: ");
                int hour = input.nextInt();
                endTime = LocalDateTime.now().withHour(hour);
            }
            default -> endTime = LocalDateTime.now().plusHours(1);
        }

        Reservation reservation = new Reservation(client, LocalDateTime.now(), endTime);
        reservationManager.addReservation(selectedStation, reservation);
    }

}
