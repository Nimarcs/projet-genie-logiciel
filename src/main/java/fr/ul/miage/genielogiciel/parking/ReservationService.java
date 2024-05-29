package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ReservationService {

    public void reserveChargingStation(Scanner input, ChargingStationList chargingStations, ClientList clients) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime endTime;
        List<ChargingStation> availableStations = chargingStations.findAvailableStations();

        if (!availableStations.isEmpty()) {
            System.out.println("Available station found: ");

            int i = 1;
            int selection = -1;

            for (ChargingStation station : availableStations) {
                System.out.println(i + ". ID: " + station.getIdStation());
                i++;
            }

            System.out.print("Please enter the ID of the station you want to reserve: ");
            selection = input.nextInt();
            input.nextLine(); // clear the newline character

            ChargingStation selectedStation = chargingStations.findChargingStation(selection);

            if (selectedStation != null && selectedStation.getDisponible()) {
                System.out.print("Please enter your license number: ");
                String licenseNumber = input.nextLine();

                Client client = clients.findClientByLicense(licenseNumber);

                if (client != null) {
                    selection = -1;
                    System.out.println("Choose what to enter: ");
                    System.out.println("1. Expected duration ");
                    System.out.println("2. Departure time ");

                    selection = checkInputMenu(input, 3);

                    switch (selection) {
                        case 1:
                            System.out.println("Enter expected duration: ");
                            int duration = input.nextInt();
                            endTime = currentTime.plusHours(duration);
                            break;
                        case 2:
                            System.out.println("Enter departure time: ");
                            int hour = input.nextInt();
                            endTime = currentTime.withHour(hour);
                            break;
                        default:
                            endTime = currentTime.plusHours(1);
                    }

                    selectedStation.addReservation(new Reservation(client, LocalDateTime.now(), endTime));
                } else {
                    System.out.print("License number not recognized. Please enter your mobile number: ");
                    String mobileNumber = input.nextLine();

                    Client client2 = clients.findByMobilePhone(mobileNumber);

                    if (client2 != null) {
                        System.out.print("Please enter the expected charging duration (in hours): ");
                        int duration = input.nextInt();

                        System.out.println("Temporary reservation made for mobile number " + mobileNumber + " with duration " + duration + " hours.");
                        selectedStation.addReservation(new Reservation(client2, currentTime, currentTime.plusHours(duration)));
                    } else {
                        System.out.println("We didn't find the account associated with this number. Please create a new one: ");
                        new CommandLine().run(input, client, clients, chargingStations);
                    }
                }
            } else {
                System.out.println("Selected station is not available.");
            }
        } else {
            System.out.println("No available stations at the moment. Please try again later.");
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

