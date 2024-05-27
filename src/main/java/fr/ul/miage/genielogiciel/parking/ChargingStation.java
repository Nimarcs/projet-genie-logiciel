package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class ChargingStation {

    // === General info of charging station ===
    private int idStation;
    private boolean isDisponible;

    public List<Reservation> reservations = new ArrayList<>();



    public ChargingStation(int idStation, boolean isDisponible) {
        setIdStation(idStation);
        setDispobnible(isDisponible);
    }

    //    === GETTERS and SETTERS ===
    public int getIdStation() { return idStation; }
    public void setIdStation(int id) { this.idStation = id; }


    public boolean getDisponible() { return isDisponible; }
    public void setDispobnible(boolean disponible) { isDisponible = disponible; }


    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.confirmReservation();
        sendNotification(reservation.client, "Reservation confirmed for " + reservation.startTime);
    }


    public void checkIn(Client client, LocalDateTime currentTime) {
    for (Reservation reservation : reservations) {
        if (reservation.client.equals(client) && !reservation.isNoShow &&
            currentTime.isAfter(reservation.startTime) && currentTime.isBefore(reservation.startTime.plusMinutes(10))) {
            // Check-in successful within waiting period
            reservation.confirmReservation();
            chargeClient(client, calculateNormalCharge(reservation));
            sendNotification(client, "Checked in successfully. Charged for the full reservation period.");
            return;
        }
        reservation.cancelReservation();
    }
    sendNotification(client, "Check-in failed or reservation not found.");
}


    public void checkOut(Client client, LocalDateTime currentTime) {
        for (Reservation reservation : reservations) {
            if (reservation.client.equals(client) && reservation.isConfirmed) {
                if (currentTime.isAfter(reservation.endTime)) {
                    reservation.isOverstayed = true;
                    double overstayCharge = calculateOverstayCharge(reservation, currentTime);
                    chargeClient(client, overstayCharge);
                    sendNotification(client, "Charged for the full reservation period and overstay charge of $" + overstayCharge);
                } else {
                    chargeClient(client, calculateNormalCharge(reservation));
                    sendNotification(client, "Charged for the full reservation period.");
                }
                reservation.cancelReservation();
                sendNotification(client, "Checked out successfully.");

                return;
            }
        }
        sendNotification(client, "Check-out failed or reservation not found.");
    }

    public void processNoShowReservations(LocalDateTime currentTime) {
        for (Reservation reservation : reservations) {
            if (currentTime.isAfter(reservation.startTime.plusMinutes(10))) {
                reservation.markNoShow();
                double chargeAmount = calculateNormalCharge(reservation);
                chargeClient(reservation.client, chargeAmount);
                sendNotification(reservation.client, "You did not show up. Charged for the full reservation period of $" + chargeAmount);
                reservation.cancelReservation();
            }
        }
    }


    private double calculateNormalCharge(Reservation reservation) {
        long duration = ChronoUnit.HOURS.between(reservation.startTime, reservation.endTime);
        return duration * reservation.normalRate;
    }

    private double calculateOverstayCharge(Reservation reservation, LocalDateTime currentTime) {
        long overstayDuration = ChronoUnit.HOURS.between(reservation.endTime, currentTime);
        double totalOverstayCharge = 0.0;
        double currentOverstayRate = reservation.overstayRate;

        for (int i = 0; i < overstayDuration; i++) {
            totalOverstayCharge += currentOverstayRate;
            currentOverstayRate *= 1.5; // Increase rate by 50% for each hour of overstay
        }

        return calculateNormalCharge(reservation) + totalOverstayCharge;
    }

    private void chargeClient(Client client, double amount) {
        sendNotification(client, "Charged: $" + amount);
    }

    private void sendNotification(Client client, String message) {
        System.out.println("Notification to " + client.getPhoneNumber() + ": " + message);
    }

    public void offerExtension(Client client, LocalDateTime currentTime) {
        for (Reservation reservation : reservations) {
            if (reservation.client.equals(client) && !reservation.isConfirmed &&
                currentTime.isAfter(reservation.startTime) && currentTime.isBefore(reservation.startTime.plusMinutes(10))) {
                if (isStationAvailable(reservation, currentTime)) {
                    sendNotification(reservation.client, "Would you like to extend your reservation? Additional charges apply.");
                    //TODO  add logic for client interaction with offer
                }
            }
        }
    }


    private boolean isStationAvailable(Reservation reservation, LocalDateTime currentTime) {
        for (Reservation res : reservations) {
            if (res != reservation && res.startTime.isBefore(currentTime.plusMinutes(10)) && res.endTime.isAfter(currentTime)) {
                return false;
            }
        }
        return true;
    }

}