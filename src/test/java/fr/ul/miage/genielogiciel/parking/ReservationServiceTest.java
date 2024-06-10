package fr.ul.miage.genielogiciel.parking;

import fr.ul.miage.genielogiciel.parking.commandLine.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    private ReservationDisplayService reservationService;
    private ArrayList<ChargingStation> chargingStations;
    private ArrayList<Client> clients;
    private ArrayList<Reservation> reservations;
    private Scanner input;
    private Displayer displayer;
    private FacadeInterface facadeInterface;
    private MenuChecker menuChecker;

    @BeforeEach
    public void setUp() {
        displayer = new DisplayerSout();
        chargingStations = new ArrayList<>();
        clients = new ArrayList<>();
        reservations = new ArrayList<>();

        ChargingStation station1 = new ChargingStation(123, true);
        ChargingStation station2 = new ChargingStation(456, true);
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

    // reservation when we don't have any available stations
    @Test
    public void testReserveNoAvailableStations() {
        for (ChargingStation station : chargingStations) {
            station.setDisponible(false);
        }

        Client client = clients.get(0);
        input = new Scanner("123\n");

        facadeInterface.setCurrentClient(client);
        menuChecker = new MenuChecker(input, displayer);
        reservationService = new ReservationDisplayService(input, displayer, menuChecker);
        reservationService.reserveChargingStation(facadeInterface, new ClientDisplayService(input, displayer));

        assertEquals(0, reservations.size());
    }

    // reservation with valid client
    @Test
    public void testReserveValidClient() {
        Client client = clients.get(0);
        input = new Scanner("123\nLIC123\n1\n1\n");

        facadeInterface.setCurrentClient(client);
        menuChecker = new MenuChecker(input, displayer);
        reservationService = new ReservationDisplayService(input, displayer, menuChecker);
        reservationService.reserveChargingStation(facadeInterface, new ClientDisplayService(input, displayer) );

        assertEquals(1, reservations.size());
        Reservation reservation = reservations.get(0);
        assertEquals("LIC123", reservation.client.getPlateNumbers());
        assertFalse(chargingStations.get(0).getDisponible()); // Ensure the station is now reserved
    }

}
