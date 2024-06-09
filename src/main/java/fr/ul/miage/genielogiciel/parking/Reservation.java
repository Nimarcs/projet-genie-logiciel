package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.util.List;

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
     * Gets the ID of the reservation.
     *
     * @return the reservation ID
     */
    public int getIdReservation() { return idReservation; }

    /**
     * Sets the ID of the reservation.
     *
     * @param idReservation the reservation ID to set
     */
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    /**
     * Gets the ID of the station.
     *
     * @return the station ID
     */
    public int getIdStation() { return idStation; }

    /**
     * Sets the ID of the station.
     *
     * @param idStation the station ID to set
     */
    public void setIdStation(int idStation) { this.idStation = idStation; }

    /**
     * Gets the plate numbers associated with the reservation.
     *
     * @return the plate numbers
     */
    public String getPlateNumbers() { return plateNumbers; }

    /**
     * Sets the plate numbers associated with the reservation.
     *
     * @param plateNumbers the plate numbers to set
     */
    public void setPlateNumbers(String plateNumbers) { this.plateNumbers = plateNumbers; }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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

    public void setNormalRate(double normalRate) {
        this.normalRate = normalRate;
    }

    public double getOverstayRate() {
        return overstayRate;
    }

    public void setOverstayRate(double overstayRate) {
        this.overstayRate = overstayRate;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public boolean isNoShow() {
        return isNoShow;
    }

    public void setNoShow(boolean noShow) {
        isNoShow = noShow;
    }
}
