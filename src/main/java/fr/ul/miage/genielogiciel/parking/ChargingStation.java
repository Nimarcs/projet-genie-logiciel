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
    private Collection<Reservation> reservations = new HashSet<>();

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
    public boolean getDisponible() {
        return isDisponible;
        //TODO add the refresh of the is disponible
    }
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

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Collection<Reservation> getReservations() {
        return Collections.unmodifiableCollection(reservations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargingStation that = (ChargingStation) o;
        return idStation == that.idStation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStation);
    }
}
