package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents a reservation for a charging station.
 */
public class Reservation {

    private Client client;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int duration;
    private boolean isOverstayed;
    private double normalRate;
    private double overstayRate;
    private boolean isConfirmed;
    private boolean isNoShow;

    private int idReservation;
    private String plateNumbers;
    private int idStation;

    /**
     * Constructs a new Reservation with the specified client, start time, end time, normal rate, and overstay rate.
     *
     * @param client the client making the reservation
     * @param startTime the start time of the reservation
     * @param endTime the end time of the reservation
     * @param normalRate the normal rate for the reservation
     * @param overstayRate the rate for overstaying the reservation
     */
    public Reservation(Client client, LocalDateTime startTime, LocalDateTime endTime, double normalRate, double overstayRate) {
        this.client = client;
        this.startTime = startTime;
        this.endTime = endTime;
        this.normalRate = normalRate;
        this.overstayRate = overstayRate;
        this.isOverstayed = false;
        this.isConfirmed = false;
        this.isNoShow = false;
    }

    /**
     * Constructs a new Reservation with the specified client, start time, and end time.
     *
     * @param client the client making the reservation
     * @param startTime the start time of the reservation
     * @param endTime the end time of the reservation
     */
    public Reservation(Client client, LocalDateTime startTime, LocalDateTime endTime) {
        int difference = endTime.getHour() - startTime.getHour();
        this.client = client;
        this.startTime = startTime;
        this.endTime = endTime;
        this.normalRate = 10.0 * difference;
        this.overstayRate = 0.0;
        this.isOverstayed = false;
        this.isConfirmed = false;
        this.isNoShow = false;
    }

    /**
     * Sets the end time of the reservation.
     *
     * @param newEndTime the new end time of the reservation
     */
    public void setEndTime(LocalDateTime newEndTime) {
        this.endTime = newEndTime;
    }



    /**
     * Confirms the reservation.
     */
    public void confirmReservation() {
        this.isConfirmed = true;
    }

    /**
     * Cancels the reservation.
     */
    public void cancelReservation() {
        this.isConfirmed = false;
    }

    /**
     * Marks the reservation as a no-show.
     */
    public void markNoShow() {
        this.isNoShow = true;
    }

    // === GETTERS and SETTERS ===


    /**
     * Gets the ID of the station.
     *
     * @return the station ID
     */
    public int getIdStation() { return idStation; }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean isOverstayed() {
        return isOverstayed;
    }

    public void setOverstayed(boolean overstayed) {
        isOverstayed = overstayed;
    }

    public double getNormalRate() {
        return normalRate;
    }


    public double getOverstayRate() {
        return overstayRate;
    }


    public boolean isConfirmed() {
        return isConfirmed;
    }


    public boolean isNoShow() {
        return isNoShow;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return idReservation == that.idReservation && idStation == that.idStation && startTime.equals(that.startTime) && endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, idReservation, idStation);
    }
}
