package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents a charging station for electric vehicles.
 */
public class ChargingStation {

    // === General info of charging station ===
    private int idStation;
    private boolean isDisponible;
    private List<Reservation> reservations = new ArrayList<>();

    /**
     * Constructs a new ChargingStation with the specified id and availability status.
     *
     * @param idStation the unique identifier of the charging station
     * @param isDisponible the availability status of the charging station
     */
    public ChargingStation(int idStation, boolean isDisponible) {
        setIdStation(idStation);
        setDisponible(isDisponible);
    }
    
    public ChargingStation() {}

    // === GETTERS and SETTERS ===
    public int getIdStation() { return idStation; }
    public void setIdStation(int id) { this.idStation = id; }
    public boolean getDisponible() { return isDisponible; }
    public void setDisponible(boolean disponible) { isDisponible = disponible; }


    /**
     * Checks if the station is available during the specified time.
     *
     * @param reservation the reservation to check availability for
     * @param currentTime the current time
     * @return true if the station is available, false otherwise
     */
    public boolean isStationAvailable(Reservation reservation, LocalDateTime currentTime) {
        for (Reservation res : reservations) {
            if (res != reservation && res.startTime.isBefore(currentTime.plusMinutes(10)) && res.endTime.isAfter(currentTime)) {
                return false;
            }
        }
        return true;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    /**
     * Find a charging station by its ID
     *
     * @param idStation the ID of the charging station to search for
     * @return the charging station with the specified ID, or null if not found
     * @see ChargingStation#getIdStation()
     */
    public ChargingStation findChargingStation(int idStation, List<ChargingStation> stations){
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
    public List<ChargingStation> findAvailableStations(List<ChargingStation> stations) {
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
    public ChargingStation findChargingStationByClient(Client client, List<ChargingStation> stations) {
        for (ChargingStation station : stations) {
            for (Reservation reservation : station.getReservations()) {
                if (reservation.client.equals(client) && reservation.isConfirmed) {
                    return station;
                }
            }
        }
        return null; // No station found for the given client
    }




}
