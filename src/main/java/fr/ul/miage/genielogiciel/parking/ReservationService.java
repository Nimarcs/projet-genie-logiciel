package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ReservationService {

    private final ReservationManager reservationManager = new ReservationManager();

    /**
     * Create a reservation for a charging station
     * @param input input of the user
     * @param chargingStations charging station to reserve
     * @param clients list of current clients
     */
    public void reserveChargingStation(Scanner input, ChargingStationList chargingStations, ClientList clients) {
        List<ChargingStation> availableStations = chargingStations.findAvailableStations();

        if (availableStations.isEmpty()) {
            System.out.println("No available stations at the moment. Please try again later.");
            return;
        }

        System.out.println("Available station found: ");

        int i = 1;
        int selection;

        for (ChargingStation station : availableStations) {
            System.out.println(i + ". ID: " + station.getIdStation());
            i++;
        }

        System.out.print("Please enter the ID of the station you want to reserve: ");
        selection = input.nextInt();
        input.nextLine(); // clear the newline character

        ChargingStation selectedStation = chargingStations.findChargingStation(selection);

        if (!(selectedStation != null && selectedStation.getDisponible())) {
            System.out.println("Selected station is not available.");
            return;
        }
            System.out.print("Please enter your license number: ");
            String licenseNumber = input.nextLine();

            Client client = clients.findClientByLicense(licenseNumber);

            if (client != null) {
                manageClientAndReserve(input, selectedStation, client);
            } else {
                findClientAndReserve(input, chargingStations, clients, selectedStation);
            }
    }

    public void handleLateArrival(Scanner input, ChargingStationList chargingStations, ClientList clients) {
        System.out.print("Please enter your license number: ");
        String licenseNumber = input.nextLine();

        Client client = clients.findClientByLicense(licenseNumber);

        if (client != null) {
            LocalDateTime currentTime = LocalDateTime.now();
            ChargingStation station = chargingStations.findChargingStationByClient(client);
            if (station != null) {
                reservationManager.handleLateArrival(station, client, currentTime, input);
            } else {
                System.out.println("No active reservation found for this client.");
            }
        } else {
            System.out.println("Client not found.");
        }
    }



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
