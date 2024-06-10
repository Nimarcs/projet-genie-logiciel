package fr.ul.miage.genielogiciel.parking;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TestChargingStation {

    ChargingStation chargingStation;
    Client client;
    Reservation reservation;
    LocalDateTime startTime;
    LocalDateTime endTime;
    ReservationManager reservationManager;


    //AFTER REFACTORING -- NEED TO ADD MORE TESTS
    @BeforeEach
    void setUp() {
        reservationManager = new ReservationManager();
        chargingStation = new ChargingStation(1,true);
        client = new Client("John","Smith","123 Street","+33681726450","sd@mail.com","1234567890123456","aa123a", "login", "passwordLong" );
        startTime = LocalDateTime.now();
        endTime = startTime.plusHours(2);
        reservation = new Reservation(client, startTime, endTime, 10.0, 20.0);
        reservationManager.addReservation(chargingStation, reservation);
    }

    @Test
    void testAddReservation() {
        assertFalse(chargingStation.getReservations().isEmpty());
        assertEquals(1, chargingStation.getReservations().size());
        assertEquals(client, chargingStation.getReservations().stream().toList().get(0).client);
    }

    @Test
    void testCheckInWithinWaitingPeriod() {
        LocalDateTime checkInTime = startTime.plusMinutes(5);
        reservationManager.checkIn(chargingStation, client, checkInTime);
        assertTrue(reservation.isConfirmed);
    }

    @Test
    void testCheckInAfterWaitingPeriod() {
        LocalDateTime checkInTime = startTime.plusMinutes(15);
        reservationManager.checkIn(chargingStation, client, checkInTime);
        assertFalse(reservation.isConfirmed);
    }

    @Test
    void testCheckOutBeforeEndTime() {
        LocalDateTime checkInTime = startTime.plusMinutes(5);
        reservationManager.checkIn(chargingStation,client, checkInTime);
        LocalDateTime checkOutTime = endTime.minusMinutes(5);
        reservationManager.checkOut(chargingStation, client, checkOutTime);
        assertFalse(reservation.isConfirmed);
    }

    @Test
    void testCheckOutAfterEndTime() {
        LocalDateTime checkInTime = startTime.plusMinutes(5);
        reservationManager.checkIn(chargingStation, client, checkInTime);
        LocalDateTime checkOutTime = endTime.plusHours(2);
        reservationManager.checkOut(chargingStation,client, checkOutTime);
        assertFalse(reservation.isConfirmed);
        assertTrue(reservation.isOverstayed);
    }

    @Test
    void testProcessNoShowReservations() {
        LocalDateTime currentTime = startTime.plusMinutes(15);
        reservationManager.processNoShowReservations(currentTime);
        assertTrue(reservation.isNoShow);
        assertFalse(reservation.isConfirmed);
    }

    @Test
    void testOfferExtension() {
        LocalDateTime currentTime = startTime.plusMinutes(5);
        reservationManager.offerExtension(chargingStation,client, currentTime);
        //TODO check that extension is proposed
    }


}


