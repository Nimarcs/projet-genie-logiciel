package fr.ul.miage.genielogiciel.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineTest {

    private CommandLine commandLine;
    private ArrayList<ChargingStation> chargingStations;
    private ArrayList<Client> clients;
    private ArrayList<Reservation> reservations;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        commandLine = new CommandLine();
        chargingStations = new ArrayList<>();
        clients = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    @Test
    void testLogin() {
        String input = "1\nvalidUser\nvalidPassword\n4\n3\n";
        scanner = new Scanner(input);

        Client client = new Client();
        client.setUsername("validUser");
        client.setPassword("validPassword");
        clients.add(client);

        commandLine.run(scanner, chargingStations, clients, reservations);


    }

    @Test
    void testRun_InvalidChoiceThenQuit() {
        String input = "4\n3\n";
        scanner = new Scanner(input);

        commandLine.run(scanner, chargingStations, clients, reservations);
    }
}
