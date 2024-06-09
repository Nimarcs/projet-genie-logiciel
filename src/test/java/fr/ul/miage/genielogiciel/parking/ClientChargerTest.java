package fr.ul.miage.genielogiciel.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientChargerTest {

    private ClientCharger clientCharger;
    private Client client;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        clientCharger = new ClientCharger();
        client = new Client("John", "Smith", "dfd street", "+33000000000", "sd@mail.com", "1234567890123456", "aa123a", "login", "p1212ass");
        reservation = new Reservation(client, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(3), 10.0, 20.0);
    }

    @Test
    void testChargeClient() {
        clientCharger.chargeClient(client, 100.0);
    }

    @Test
    void testCalculateNormalCharge() {
        double charge = clientCharger.calculateNormalCharge(reservation);
        assertEquals(20.0, charge);
    }

    @Test
    void testCalculateOverstayChargeNoOverstay() {
        LocalDateTime currentTime = reservation.getEndTime();
        double charge = clientCharger.calculateOverstayCharge(reservation, currentTime);
        assertEquals(20.0, charge);
    }

    @Test
    void testCalculateOverstayChargeOneHourOverstay() {
        LocalDateTime currentTime = reservation.getEndTime().plusHours(1);
        double charge = clientCharger.calculateOverstayCharge(reservation, currentTime);
        assertEquals(40.0, charge);
    }

    @Test
    void testCalculateOverstayChargeTwoHoursOverstay() {
        LocalDateTime currentTime = reservation.getEndTime().plusHours(2);
        double charge = clientCharger.calculateOverstayCharge(reservation, currentTime);
        assertEquals(70.0, charge); // 20.0 normal charge + 20.0 first hour + 30.0 second hour
    }

    @Test
    void testCalculateOverstayChargeMultipleHoursOverstay() {
        LocalDateTime currentTime = reservation.getEndTime().plusHours(3);
        double charge = clientCharger.calculateOverstayCharge(reservation, currentTime);
        assertEquals(115.0, charge, 0.01); // 20.0 normal charge + 20.0 first hour + 30.0 second hour + 45.0 third hour
    }
}
