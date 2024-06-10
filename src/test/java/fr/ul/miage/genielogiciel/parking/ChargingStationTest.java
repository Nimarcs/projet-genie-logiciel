package fr.ul.miage.genielogiciel.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChargingStationTest {

    private ChargingStation station;
    private List<ChargingStation> stations;
    private Client client;

    @BeforeEach
    void setUp() {
        station = new ChargingStation(1, true);
        stations = new ArrayList<>();
        stations.add(station);
        client = new Client("John","Smith","dfd street","+33000000000","sd@mail.com","1234567890123456","aa123a", "login", "p1212ass" );
    }

    @Test
    void testSetAndGetReservations() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(client, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        station.setReservations(reservations);
        assertEquals(reservations, station.getReservations());
    }

    @Test
    void testIsStationAvailable() {
        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = new Reservation(client, now.plusHours(1), now.plusHours(2));
        station.setReservations(List.of(reservation));

        assertTrue(station.isStationAvailable(new Reservation(client, now, now.plusHours(1)), now));
        assertFalse(station.isStationAvailable(new Reservation(client, now.plusHours(1).plusMinutes(5), now.plusHours(2)), now.plusHours(1).plusMinutes(5)));
    }

    @Test
    void testIsStationAvailableDuringInterval() {
        LocalDateTime now = LocalDateTime.now();
        Reservation reservation = new Reservation(client, now.plusHours(1), now.plusHours(2));
        station.setReservations(List.of(reservation));

        assertTrue(station.isStationAvailableDuringInterval(now, now.plusMinutes(30)));
        assertFalse(station.isStationAvailableDuringInterval(now.plusHours(1).minusMinutes(15), now.plusHours(1).plusMinutes(15)));
    }

}
