package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
//import java.util.Date;

/**
 * Represents a reservation for a charging station.
 */
public class Reservation {

    public Client client;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public int duration;
    public boolean isOverstayed;
    public double normalRate;
    public double overstayRate;
    public boolean isConfirmed;
    public boolean isNoShow;

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
    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    public int getIdStation() { return idStation; }
    public void setIdStation(int idStation) { this.idStation = idStation; }

    public String getPlateNumbers() { return plateNumbers; }
    public void setPlateNumbers(String plateNumbers) { this.plateNumbers = plateNumbers; }
}

