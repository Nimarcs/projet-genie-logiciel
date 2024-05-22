package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
//import java.util.Date;

public class Reservation {

    Client client;
    LocalDateTime startTime;
    LocalDateTime endTime;
    boolean isOverstayed;
    double normalRate;
    double overstayRate;
    boolean isConfirmed;
    boolean isNoShow;

    private int idReservation;
    private String plateNumbers;
    private int idStation;

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

    public void confirmReservation() {
        this.isConfirmed = true;
    }

    public void cancelReservation() {
        this.isConfirmed = false;
    }

    public void markNoShow() {
        this.isNoShow = true;
    }

    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    public int getIdStation() { return idStation; }
    public void setIdStation(int idStation) { this.idStation = idStation; }

    public String getPlateNumbers() { return plateNumbers; }
    public void setPlateNumbers(String plateNumbers) { this.plateNumbers = plateNumbers; }
}


// test test