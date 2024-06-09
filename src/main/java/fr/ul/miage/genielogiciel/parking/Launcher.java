package fr.ul.miage.genielogiciel.parking;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Launcher {


    /**
     * Entry point for the FastBorne application.
     * <p>
     * This method initializes the CommandLine object and starts the application by calling the run method.
     * </p>
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        // Create client instances
        Client client1 = new Client();
        Client client2 = new Client("Lara", "Mara", "39 rue Paris, 54000 Nancy", "+33785546942", "test@gmail.com", "0001234223415435", "LIC123", "test", "test2test");

        // Create lists
        ArrayList<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

        // Create charging station list
        ArrayList<ChargingStation> chargingStations = new ArrayList<>();

        chargingStations.add(new ChargingStation(123, true));
        chargingStations.add(new ChargingStation(456, true));
        chargingStations.add(new ChargingStation(789, false));
        chargingStations.add(new ChargingStation(12, false));

        // Define reservation times
        LocalDateTime startTime = LocalDateTime.of(2023, 6, 10, 10, 0); // Example start time
        LocalDateTime endTime = LocalDateTime.of(2023, 6, 10, 12, 0);

        // Create reservation list
        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(client2, startTime, endTime));


        Scanner scanner = new Scanner(System.in);


        CommandLine commandLine = new CommandLine();
        commandLine.run(scanner, client2, chargingStations, clients, reservations);
    }


}
