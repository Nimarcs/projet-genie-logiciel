package fr.ul.miage.genielogiciel.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ReservationManagerTest {

    private ReservationManager reservationManager;
    private ChargingStation station;
    private Client client;
    private ClientNotifier clientNotifier;
    private ClientCharger clientCharger;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservationManager = new ReservationManager();
        station = new ChargingStation(1, true);
        client = new Client("John", "Smith", "dfd street", "+33000000000", "sd@mail.com", "1234567890123456", "aa123a", "login", "p1212ass");
        clientNotifier = new ClientNotifier();
        clientCharger = new ClientCharger();
        reservation = new Reservation(client, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), 10.0, 20.0);

        reservationManager.add(reservation);
    }

    @Test
    void testCheckInSuccessful() {
        LocalDateTime currentTime = reservation.getStartTime().plusMinutes(5);
        reservationManager.checkIn(station, client, currentTime);
        assertTrue(reservation.isConfirmed());
        assertEquals(10.0, clientCharger.calculateNormalCharge(reservation));
    }

    @Test
    void testCheckInFailed() {
        LocalDateTime currentTime = reservation.getStartTime().minusMinutes(5);
        reservationManager.checkIn(station, client, currentTime);
        assertFalse(reservation.isConfirmed());
    }

    @Test
    void testCheckInAfterWaitingPeriod() {
        LocalDateTime checkInTime = reservation.getStartTime().plusMinutes(15);
        reservationManager.checkIn(station, client, checkInTime);
        assertFalse(reservation.isConfirmed());
    }

    @Test
    void testCheckOutSuccessful() {
        LocalDateTime currentTime = reservation.getEndTime().minusMinutes(5);

        reservationManager.checkOut(station, client, currentTime);

        assertFalse(reservation.isConfirmed());
        assertEquals(10.0, clientCharger.calculateNormalCharge(reservation));
    }


    @Test
    void testCheckOutAfterEndTime() {
        LocalDateTime checkInTime = reservation.getStartTime().plusMinutes(5);
        reservationManager.checkIn(station, client, checkInTime);
        LocalDateTime checkOutTime = reservation.getEndTime().plusHours(2);
        reservationManager.checkOut(station,client, checkOutTime);
        assertFalse(reservation.isConfirmed());
        assertTrue(reservation.isOverstayed());

    }

    @Test
    void testProcessNoShowReservations() {
        LocalDateTime currentTime = reservation.getStartTime().plusMinutes(15);

        reservationManager.processNoShowReservations(currentTime);

        assertTrue(reservation.isNoShow());
        assertEquals(10.0, clientCharger.calculateNormalCharge(reservation));
        assertFalse(reservation.isConfirmed());
    }

    @Test
    void testOfferExtension() {
        LocalDateTime currentTime = reservation.getStartTime().plusMinutes(5);
        reservationManager.offerExtension(station, client, currentTime);
        //TODO check that extension is proposed

    }

    @Test
    void testHandleLateArrival() {
        LocalDateTime currentTime = reservation.getStartTime().plusMinutes(15);
        Scanner input = new Scanner("2\n"); // Simulate user input

        reservationManager.handleLateArrival(station, client, currentTime, input);

        assertEquals(reservation.getEndTime(), currentTime.plusHours(2));
        assertEquals(20.0, clientCharger.calculateNormalCharge(reservation));
    }


    // There is no available stations for that hour
    @Test
    void testLateArrivalNoAvailableStations() {
        LocalDateTime currentTime = LocalDateTime.now();

        Scanner input = new Scanner("2\n");
        // Set the charging station to not be available
        station.setDisponible(false);
        reservationManager.handleLateArrival(station, client, currentTime, input);
        assertNotEquals(currentTime.plusHours(1), reservation.getEndTime());

    }

    @Test
    void testLateArrivalReservationNotFound() {
        reservationManager.clear(); // Clear reservations to simulate reservation not found
        LocalDateTime currentTime = LocalDateTime.now();
        Scanner input = new Scanner(System.in);
        reservationManager.handleLateArrival(station, client, currentTime, input);
    }
}
