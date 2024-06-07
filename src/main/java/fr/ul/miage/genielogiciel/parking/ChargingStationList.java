package fr.ul.miage.genielogiciel.parking;

import java.util.ArrayList;
import java.util.List;

/**
 * List of {@link ChargingStation} in the parking system.
 * <p> Provides methods:
 * <ul>
 *   <li>{@link ChargingStationList#addStation(ChargingStation)} - add charging station to the list</li>
 *   <li>{@link ChargingStationList#findChargingStation(int)} - find charging station by ID </li>
 *      <li>{@link ChargingStationList#findAvailableStations()} - find all available charging station in the parking</li>
 * </ul>
 *</p>
 *
 * @since 1.0
 */

public class ChargingStationList {

    private final List<ChargingStation> stations;

    /**
     * Create a new list of charging stations.
     * Initialize an empty list of stations.
     */
    public ChargingStationList() {
        stations = new ArrayList<>();
    }


    /**
     * Add a charging station to the list
     *
     * @param station the charging station to be added
     */
    public void addStation(ChargingStation station) {
        stations.add(station);
    }


    /**
     * Find a charging station by its ID
     *
     * @param idStation the ID of the charging station to search for
     * @return the charging station with the specified ID, or null if not found
     * @see ChargingStation#getIdStation()
     */
    public ChargingStation findChargingStation(int idStation){
        for (ChargingStation station: stations){
            if (station.getIdStation() == idStation) return station;
        }
        return null;
    }

    /**
     * Finds all available charging stations.
     *
     * @return a list of available charging stations
     * @see ChargingStation#getDisponible()
     */
    public List<ChargingStation> findAvailableStations() {
        List<ChargingStation> availableStations = new ArrayList<>();
        for (ChargingStation station : stations) {
            if (station.getDisponible()) {
                availableStations.add(station);
            }
        }
        return availableStations;
    }


    /**
     * Finds the charging station that has a reservation for the given client.
     *
     * @param client the client to find the charging station for
     * @return the charging station with a reservation for the client, or null if not found
     */
    public ChargingStation findChargingStationByClient(Client client) {
        for (ChargingStation station : stations) {
            for (Reservation reservation : station.reservations) {
                if (reservation.client.equals(client) && reservation.isConfirmed) {
                    return station;
                }
            }
        }
        return null; // No station found for the given client
    }
}
