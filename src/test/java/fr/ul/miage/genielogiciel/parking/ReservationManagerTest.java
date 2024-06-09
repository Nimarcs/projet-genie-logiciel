package fr.ul.miage.genielogiciel.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ReservationManagerTest {

    private ReservationManager reservationManager;
    private ChargingStation chargingStation;
    private Client client;
    private Reservation reservation;
    private ClientNotifier clientNotifier;
    private ClientCharger clientCharger;
    private Scanner input;

    @BeforeEach
    void setUp() {
        reservationManager = new ReservationManager();
        chargingStation = new ChargingStation();
        client = new Client();
        client.setName("John");
        client.setPhoneNumber("0786656273");

        reservation = new Reservation();
        reservation.client = client;
        reservation.startTime = LocalDateTime.now().minusMinutes(20);
        reservation.endTime = LocalDateTime.now().plusHours(1);

        clientNotifier = new ClientNotifier();
        clientCharger = new ClientCharger();

        input = new Scanner(System.in);
        reservationManager.add(reservation);

        // Inject clientNotifier and clientCharger using reflection
        try {
            var clientNotifierField = ReservationManager.class.getDeclaredField("clientNotifier");
            clientNotifierField.setAccessible(true);
            clientNotifierField.set(reservationManager, clientNotifier);

            var clientChargerField = ReservationManager.class.getDeclaredField("clientCharger");
            clientChargerField.setAccessible(true);
            clientChargerField.set(reservationManager, clientCharger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // ================= LATE ARRIVAL  =================

    // Verify that the client was notified correctly

    // Succesfull adding the hours to reservation
    @Test
    void testLateArrivalSuccessful() {
        LocalDateTime currentTime = LocalDateTime.now();

        input = new Scanner("2\n");

        reservationManager.handleLateArrival(chargingStation, client, currentTime, input);

        assertEquals(currentTime.plusHours(2), reservation.getEndTime());


    }

    // There is no available stations for that hour
    @Test
    void testLateArrivalNoAvailableStations() {
        LocalDateTime currentTime = LocalDateTime.now();

        input = new Scanner("2\n");
        // Set the charging station to not be available
        chargingStation.setDisponible(false);
        reservationManager.handleLateArrival(chargingStation, client, currentTime, input);

        assertNotEquals(currentTime.plusHours(1), reservation.getEndTime());


    }

    // There is no reservation
    @Test
    void testLateArrivalReservationNotFound() {
        reservationManager.clear(); // Clear reservations to simulate reservation not found
        LocalDateTime currentTime = LocalDateTime.now();

        reservationManager.handleLateArrival(chargingStation, client, currentTime, input);

    }
}
