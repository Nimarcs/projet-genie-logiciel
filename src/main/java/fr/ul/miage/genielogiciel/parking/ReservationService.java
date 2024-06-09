package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * This service class provides functionalities for managing reservations
 * of charging stations in a parking lot.
 */
public class ReservationService {

    private final ReservationManager reservationManager = new ReservationManager();

    /**
     * Create a reservation for a charging station.
     *
     * @param input            the input scanner for user interaction
     * @param client           the client making the reservation
     * @param chargingStations the list of charging stations to reserve
     * @param clients
     * @param reservations     the list of current reservations
     */
    public void reserveChargingStation(Scanner input, Client client, ArrayList<ChargingStation> chargingStations, ArrayList<Client> clients, ArrayList<Reservation> reservations) {
        List<ChargingStation> availableStations = findAvailableStations(chargingStations);

        if (availableStations.isEmpty()) {
            System.out.println("No available stations at the moment. Please try again later.");
            return;
        }

        System.out.println("Available stations found: ");
        for (int i = 0; i < availableStations.size(); i++) {
            System.out.println((i + 1) + ". ID: " + availableStations.get(i).getIdStation());
        }

        System.out.print("Please enter the ID of the station you want to reserve (ex. 123 or 456): ");
        int selection = input.nextInt();
        input.nextLine();

        Optional<ChargingStation> selectedStationOpt = findChargingStation(chargingStations, selection);

        if (selectedStationOpt.isEmpty() || !selectedStationOpt.get().getDisponible()) {
            System.out.println("Selected station is not available.");
            return;
        }

        ChargingStation selectedStation = selectedStationOpt.get();

        Optional<Reservation> existingReservationOpt = reservations.stream()
                .filter(reservation -> reservation.client.getPlateNumbers().equals(client.getPlateNumbers()))
                .findFirst();

        if (existingReservationOpt.isPresent()) {
            System.out.println("You already have an existing reservation.");
            return;
        }

        manageClientAndReserve(input, selectedStation, client, reservations);
    }
    /**
     * View the reservation status of a client.
     *
     * @param input the input scanner for user interaction
     * @param client the client whose reservation status is to be viewed
     * @param chargingStations the list of charging stations
     * @param reservationManager the manager responsible for reservations
     * @param reservations the list of current reservations
     */
    public void viewReservationStatus(Scanner input, Client client, ArrayList<ChargingStation> chargingStations, ReservationManager reservationManager, ArrayList<Reservation> reservations) {
        LocalDateTime currentTime = LocalDateTime.now();
        Optional<ChargingStation> stationOpt = findChargingStationByClient(chargingStations, client);

        if (stationOpt.isEmpty()) {
            System.out.println("No active reservation found.");
            return;
        }

        ChargingStation station = stationOpt.get();
        Optional<Reservation> reservationOpt = findReservationByClient(reservations, client);

        if (reservationOpt.isEmpty()) {
            System.out.println("No active reservation found.");
            return;
        }

        Reservation reservation = reservationOpt.get();

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

    /**
     * View the available charging stations and optionally reserve one.
     *
     * @param input the input scanner for user interaction
     * @param client the client
     * @param chargingStations the list of charging stations
     * @param reservationService the service responsible for reservations
     * @param clients the list of current clients
     * @param reservations the list of current reservations
     */
    public void viewAvailableStations(Scanner input, Client client, ArrayList <ChargingStation> chargingStations, ReservationService reservationService, ArrayList<Client> clients, ArrayList<Reservation> reservations) {
        List<ChargingStation> availableStations = findAvailableStations(chargingStations);

        if (!availableStations.isEmpty()) {
            System.out.println("Available stations:");
            for (ChargingStation station : availableStations) {
                System.out.println("ID: " + station.getIdStation());
            }

            System.out.println("Would you like to reserve a station? (yes/no)");
            String choice = input.nextLine().trim().toLowerCase();
            if (choice.equals("yes")) {
                reservationService.reserveChargingStation(input, client, chargingStations, clients, reservations);
            }
        } else {
            System.out.println("No available stations at the moment.");
        }
    }










    // ======= MARCUS - don't know what do to with it ==========

    /**
     * Find available charging stations.
     *
     * @param chargingStations the list of charging stations
     * @return the list of available charging stations
     */
    private List<ChargingStation> findAvailableStations(ArrayList<ChargingStation> chargingStations) {
        List<ChargingStation> availableStations = new ArrayList<>();
        for (ChargingStation station : chargingStations) {
            if (station.getDisponible()) {
                availableStations.add(station);
            }
        }
        return availableStations;
    }

    /**
     * Find a charging station by its ID.
     *
     * @param chargingStations the list of charging stations
     * @param idStation the ID of the charging station
     * @return an optional containing the charging station if found, otherwise empty
     */
    private Optional<ChargingStation> findChargingStation(ArrayList<ChargingStation> chargingStations, int idStation) {
        return chargingStations.stream()
                .filter(station -> station.getIdStation() == idStation)
                .findFirst();
    }

    /**
     * Find a client by their license number.
     *
     * @param clients the list of clients
     * @param licenseNumber the license number of the client
     * @return an optional containing the client if found, otherwise empty
     */
    private Optional<Client> findClientByLicense(ArrayList<Client> clients, String licenseNumber) {
        return clients.stream()
                .filter(client -> client.getPlateNumbers().equals(licenseNumber))
                .findFirst();
    }

    /**
     * Find a charging station reserved by a client.
     *
     * @param chargingStations the list of charging stations
     * @param client the client who reserved the charging station
     * @return an optional containing the charging station if found, otherwise empty
     */
    private Optional<ChargingStation> findChargingStationByClient(ArrayList<ChargingStation> chargingStations, Client client) {
        return chargingStations.stream()
                .filter(station -> station.getReservations().stream()
                        .anyMatch(reservation -> reservation.client.equals(client) && reservation.isConfirmed))
                .findFirst();
    }

    /**
     * Find a reservation by a client.
     *
     * @param reservations the list of reservations
     * @param client the client whose reservation is to be found
     * @return an optional containing the reservation if found, otherwise empty
     */
    private Optional<Reservation> findReservationByClient(ArrayList<Reservation> reservations, Client client) {
        return reservations.stream()
                .filter(reservation -> reservation.client.equals(client) && reservation.isConfirmed)
                .findFirst();
    }

    // =====================================














    /**
     * Check the input from the menu.
     *
     * @param input the input scanner for user interaction
     * @param maxPointsMenu the maximum number of menu points
     * @return the selected menu option
     */
    private int checkInputMenu(Scanner input, int maxPointsMenu) {
        int selection;

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

    /**
     * Manage the reservation for an existing client.
     *
     * @param input the input scanner for user interaction
     * @param selectedStation the selected charging station
     * @param client the client making the reservation
     * @param reservations the list of current reservations
     */
    private void manageClientAndReserve(Scanner input, ChargingStation selectedStation, Client client, ArrayList<Reservation> reservations) {
        int selection;
        LocalDateTime endTime;
        System.out.println("Choose what to enter: ");
        System.out.println("1. Expected duration ");
        System.out.println("2. Departure time ");

        selection = checkInputMenu(input, 2);

        switch (selection) {
            case 1 -> {
                System.out.print("Enter expected duration (hours): ");
                int duration = input.nextInt();
                endTime = LocalDateTime.now().plusHours(duration);
            }
            case 2 -> {
                System.out.print("Enter departure time (hour): ");
                int hour = input.nextInt();
                endTime = LocalDateTime.now().withHour(hour);
            }
            default -> throw new IllegalStateException("Unexpected value: " + selection);
        }

        Reservation reservation = new Reservation(client, LocalDateTime.now(), endTime);
        reservation.confirmReservation();  // Confirm the reservation
        reservationManager.addReservation(selectedStation, reservation);
        reservations.add(reservation);
        selectedStation.setDisponible(false); // Mark station as not available
        System.out.println("Reservation successful!");
    }

    /**
     * Find a client by their mobile phone number and reserve a station for them.
     *
     * @param input the input scanner for user interaction
     * @param chargingStations the list of charging stations
     * @param clients the list of clients
     * @param selectedStation the selected charging station
     * @param reservations the list of current reservations
     */
    private void findClientAndReserve(Scanner input, ArrayList<ChargingStation> chargingStations, ArrayList<Client> clients, ChargingStation selectedStation, ArrayList<Reservation> reservations) {
        System.out.print("License number not recognized. Please enter your mobile number: ");
        String mobileNumber = input.nextLine();

        Optional<Client> clientOpt = findClientByPhone(clients, mobileNumber);

        if (clientOpt.isPresent()) {
            manageClientAndReserve(input, selectedStation, clientOpt.get(), reservations);
        } else {
            System.out.println("We didn't find the account associated with this number. Please create a new one.");
        }
    }

    /**
     * Find a client by their mobile phone number.
     *
     * @param clients the list of clients
     * @param phone the phone number of the client
     * @return an optional containing the client if found, otherwise empty
     */
    private Optional<Client> findClientByPhone(ArrayList<Client> clients, String phone) {
        return clients.stream()
                .filter(client -> client.getPhoneNumber().equals(phone))
                .findFirst();
    }
}
