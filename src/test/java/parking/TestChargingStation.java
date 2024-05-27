package parking;
import static org.junit.jupiter.api.Assertions.*;

import fr.ul.miage.genielogiciel.parking.ChargingStation;
import fr.ul.miage.genielogiciel.parking.Client;
import fr.ul.miage.genielogiciel.parking.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class TestChargingStation {

    ChargingStation manager;
    Client client;
    Reservation reservation;
    LocalDateTime startTime;
    LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        manager = new ChargingStation(1,true);
        client = new Client("John","Smith","dfd street","+330000","sd@mail.com","1234567890123456","aa123a", "login", "pass" );
        startTime = LocalDateTime.now();
        endTime = startTime.plusHours(1);
        reservation = new Reservation(client, startTime, endTime, 10.0, 20.0);
        manager.addReservation(reservation);
    }

    @Test
    void testAddReservation() {
        assertFalse(manager.reservations.isEmpty());
        assertEquals(1, manager.reservations.size());
        assertEquals(client, manager.reservations.get(0).client);
    }

    @Test
    void testCheckInWithinWaitingPeriod() {
        LocalDateTime checkInTime = startTime.plusMinutes(5);
        manager.checkIn(client, checkInTime);
        assertTrue(reservation.isConfirmed);
    }

    @Test
    void testCheckInAfterWaitingPeriod() {
        LocalDateTime checkInTime = startTime.plusMinutes(15);
        manager.checkIn(client, checkInTime);
        assertFalse(reservation.isConfirmed);
    }

    @Test
    void testCheckOutBeforeEndTime() {
        LocalDateTime checkInTime = startTime.plusMinutes(5);
        manager.checkIn(client, checkInTime);
        LocalDateTime checkOutTime = endTime.minusMinutes(5);
        manager.checkOut(client, checkOutTime);
        assertFalse(reservation.isConfirmed);
    }

    @Test
    void testCheckOutAfterEndTime() {
        LocalDateTime checkInTime = startTime.plusMinutes(5);
        manager.checkIn(client, checkInTime);
        LocalDateTime checkOutTime = endTime.plusHours(2);
        manager.checkOut(client, checkOutTime);
        assertFalse(reservation.isConfirmed);
        assertTrue(reservation.isOverstayed);
    }

    @Test
    void testProcessNoShowReservations() {
        LocalDateTime currentTime = startTime.plusMinutes(15);
        manager.processNoShowReservations(currentTime);
        assertTrue(reservation.isNoShow);
//        assertFalse(reservation.isConfirmed);
    }
}


