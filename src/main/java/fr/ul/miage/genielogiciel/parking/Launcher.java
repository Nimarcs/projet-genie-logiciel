package fr.ul.miage.genielogiciel.parking;


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

        // for testing
        ClientList clients = new ClientList();

        Client client1 = new Client();
        Client client2 = new Client("Lara", "Mara", "39 rue Paris, 54000 Nancy", "+33785546942", "test@gmail.com", "00012342", "LICENSE123", "test", "test2test");


        clients.addClient(client1);
        clients.addClient(client2);



        ChargingStationList chargingStations = new ChargingStationList();

        chargingStations.addStation(new ChargingStation(123, true));
        chargingStations.addStation(new ChargingStation(456, true));
        chargingStations.addStation(new ChargingStation(789, false));
        chargingStations.addStation(new ChargingStation(12, false));

        Scanner scanner = new Scanner(System.in);


        CommandLine commandLine = new CommandLine();
        commandLine.run(scanner, client2, clients, chargingStations);
    }


}
