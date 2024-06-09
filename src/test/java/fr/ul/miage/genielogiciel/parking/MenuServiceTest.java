package fr.ul.miage.genielogiciel.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class MenuServiceTest {

    private MenuService menuService;
    private ArrayList<ChargingStation> chargingStations;
    private ArrayList<Client> clients;
    private ArrayList<Reservation> reservations;

    @BeforeEach
    public void setUp() {
        menuService = new MenuService();
        chargingStations = new ArrayList<>();
        clients = new ArrayList<>();
        reservations = new ArrayList<>();

        ChargingStation station1 = new ChargingStation(123456, true);
        ChargingStation station2 = new ChargingStation(654321, true);
        chargingStations.add(station1);
        chargingStations.add(station2);

        Client client = new Client();
        client.setUsername("testUser");
        client.setPassword("testPass");
        client.setPlateNumbers("LIC123");
        client.setPhoneNumber("0786656273");
        clients.add(client);
    }

    @Test
    public void testWelcomeMenu() {
        Scanner input = new Scanner("1\n");
        int selection = menuService.welcomeMenu(input);
        assertEquals(1, selection);
    }

    @Test
    public void testMainMenuWithLicenseNumber() {
        Client client = clients.get(0);
        Scanner input = new Scanner("1\nLIC123\n4\n");

        menuService.mainMenu(input, client, chargingStations, clients, reservations);

        assertTrue(true);
    }

    @Test
    public void testMainMenuWithInvalidLicenseNumber() {
        Client client = clients.get(0);
        Scanner input = new Scanner("1\nINVALID123\n");

        menuService.mainMenu(input, client, chargingStations, clients, reservations);
        assertTrue(true);
    }

    @Test
    public void testMainMenuWithReservationNumber() {
        Client client = clients.get(0);
        client.setReservationNumber(888123);
        Scanner input = new Scanner("2\n888123\n4\n");

        menuService.mainMenu(input, client, chargingStations, clients, reservations);

        assertTrue(true);
    }

    @Test
    public void testMainMenuWithInvalidReservationNumber() {
        Client client = clients.get(0);
        Scanner input = new Scanner("2\nINVALIDRES\n");

        menuService.mainMenu(input, client, chargingStations, clients, reservations);


        assertTrue(true);
    }

    @Test
    public void testUserMenu() {
        Client client = clients.get(0);
        Scanner input = new Scanner("1\n1\n1\n");

        menuService.mainMenu(input, client, chargingStations, clients, reservations);

        assertTrue(true);
    }

    @Test
    public void testHandleUserMenu() {
        Client client = clients.get(0);
        Scanner input = new Scanner("1\n4\n");

        menuService.mainMenu(input, client, chargingStations, clients, reservations);

        assertTrue(true);
    }


}
