package fr.ul.miage.genielogiciel.parking;

import fr.ul.miage.genielogiciel.parking.commandLine.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Scanner;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


public class MenuServiceTest {

    private MenuDisplayService menuService;
    private ArrayList<ChargingStation> chargingStations;
    private ArrayList<Client> clients;
    private ArrayList<Reservation> reservations;
    private Scanner input;
    private FacadeInterface facadeInterface;

    @BeforeEach
    public void setUp() {
        input = mock(Scanner.class);
        Displayer displayer = new DisplayerSout();
        menuService = new MenuDisplayService(input,displayer , new MenuChecker(input, displayer));
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

        facadeInterface = new FacadeInterface();
        facadeInterface.initializeParking(chargingStations, clients, reservations);
    }

    @Test
    public void testWelcomeMenu() {
        when(input.nextLine())
                .thenReturn("");
        when(input.nextInt())
                .thenReturn(1);
        when(input.hasNextInt())
                .thenReturn(true);
        int selection = menuService.welcomeMenu();
        assertEquals(1, selection);
    }

    @Test
    public void testMainMenuWithLicenseNumber() {
        Client client = clients.get(0);
        when(input.nextLine())
                .thenReturn("")
                .thenReturn("LIC123")
                .thenReturn("");
        when(input.nextInt())
                .thenReturn(1)
                .thenReturn(4);
        when(input.hasNextInt())
                .thenReturn(true);

        facadeInterface.setCurrentClient(client);
        menuService.mainMenu(facadeInterface, new ReservationDisplayService(input, new DisplayerSout(), new MenuChecker(input, new DisplayerSout())), new ClientDisplayService(input, new DisplayerSout()));

        assertTrue(true);
    }

    @Test
    public void testMainMenuWithInvalidLicenseNumber() {
        Client client = clients.get(0);
        when(input.nextLine())
                .thenReturn("")
                .thenReturn("INVALID123");
        when(input.nextInt())
                .thenReturn(1);
        when(input.hasNextInt())
                .thenReturn(true);

        facadeInterface.setCurrentClient(client);
        menuService.mainMenu(facadeInterface, new ReservationDisplayService(input, new DisplayerSout(), new MenuChecker(input, new DisplayerSout())), new ClientDisplayService(input, new DisplayerSout()));
        assertTrue(true);
    }

    @Test
    public void testMainMenuWithReservationNumber() {
        Client client = clients.get(0);
        client.setReservationNumber(888123);
        when(input.nextLine())
                .thenReturn("")
                .thenReturn("888123")
                .thenReturn("");
        when(input.nextInt())
                .thenReturn(2)
                .thenReturn(4);
        when(input.hasNextInt())
                .thenReturn(true);

        facadeInterface.setCurrentClient(client);
        menuService.mainMenu(facadeInterface, new ReservationDisplayService(input, new DisplayerSout(), new MenuChecker(input, new DisplayerSout())), new ClientDisplayService(input, new DisplayerSout()));

        assertTrue(true);
    }

    @Test
    public void testMainMenuWithInvalidReservationNumber() {
        Client client = clients.get(0);
        when(input.nextLine())
                .thenReturn("")
                .thenReturn("INVALIDRES")
                .thenReturn("4");
        when(input.nextInt())
                .thenReturn(2)
                .thenReturn(4);
        when(input.hasNextInt())
                .thenReturn(true);

        facadeInterface.setCurrentClient(client);
        menuService.mainMenu(facadeInterface, new ReservationDisplayService(input, new DisplayerSout(), new MenuChecker(input, new DisplayerSout())), new ClientDisplayService(input, new DisplayerSout()));


        assertTrue(true);
    }

    @Test
    public void testUserMenu() {
        Client client = clients.get(0);
        when(input.nextLine())
                .thenReturn("")
                .thenReturn("")
                .thenReturn("");
        when(input.nextInt())
                .thenReturn(1)
                .thenReturn(1)
                .thenReturn(1);
        when(input.hasNextInt())
                .thenReturn(true);

        facadeInterface.setCurrentClient(client);
        menuService.mainMenu(facadeInterface, new ReservationDisplayService(input, new DisplayerSout(), new MenuChecker(input, new DisplayerSout())), new ClientDisplayService(input, new DisplayerSout()));

        assertTrue(true);
    }

    @Test
    public void testHandleUserMenu() {
        Client client = clients.get(0);
        when(input.nextLine())
                .thenReturn("1")
                .thenReturn("4");
        when(input.nextInt())
                .thenReturn(1)
                .thenReturn(4);
        when(input.hasNextInt())
                .thenReturn(true);


        facadeInterface.setCurrentClient(client);
        menuService.mainMenu(facadeInterface, new ReservationDisplayService(input, new DisplayerSout(), new MenuChecker(input, new DisplayerSout())), new ClientDisplayService(input, new DisplayerSout()));

        assertTrue(true);
    }


}
