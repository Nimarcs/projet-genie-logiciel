package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Represents a charging station for electric vehicles.
 */
public class ChargingStation {

    // === General info of charging station ===
    private int idStation;
    private boolean isDisponible;
    public List<Reservation> reservations = new ArrayList<>();

    /**
     * Constructs a new ChargingStation with the specified id and availability status.
     *
     * @param idStation the unique identifier of the charging station
     * @param isDisponible the availability status of the charging station
     */
    public ChargingStation(int idStation, boolean isDisponible) {
        setIdStation(idStation);
        setDispobnible(isDisponible);
    }

    // === GETTERS and SETTERS ===
    public int getIdStation() { return idStation; }
    public void setIdStation(int id) { this.idStation = id; }
    public boolean getDisponible() { return isDisponible; }
    public void setDispobnible(boolean disponible) { isDisponible = disponible; }

    /**
     * Adds a reservation to the charging station and confirms it.
     *
     * @param reservation the reservation to add
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.confirmReservation();
        sendNotification(reservation.client, "Reservation confirmed for " + reservation.startTime);
    }

    /**
     * Checks in a client to their reservation if within the waiting period.
     *
     * @param client the client to check in
     * @param currentTime the current time
     */
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

    /**
     * Checks out a client from their reservation and handles overstay charges if applicable.
     *
     * @param client the client to check out
     * @param currentTime the current time
     */
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

    /**
     * Processes reservations where the client did not show up within the waiting period.
     *
     * @param currentTime the current time
     */
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

    /**
     * Offers an extension to a client before the waiting period ends.
     *
     * @param client the client to offer the extension to
     * @param currentTime the current time
     */
    public void offerExtension(Client client, LocalDateTime currentTime) {
        for (Reservation reservation : reservations) {
            if (reservation.client.equals(client) && currentTime.isAfter(reservation.startTime) && currentTime.isBefore(reservation.startTime.plusMinutes(10))) {
                if (isStationAvailable(reservation, currentTime)) {
                    sendNotification(reservation.client, "Would you like to extend your reservation? Additional charges apply.");
                }
            }
        }
    }

    /**
     * Calculates the normal charge for a reservation.
     *
     * @param reservation the reservation to calculate the charge for
     * @return the normal charge for the reservation
     */
    private double calculateNormalCharge(Reservation reservation) {
        long duration = ChronoUnit.HOURS.between(reservation.startTime, reservation.endTime);
        return duration * reservation.normalRate;
    }

    /**
     * Calculates the overstay charge for a reservation.
     *
     * @param reservation the reservation to calculate the overstay charge for
     * @param currentTime the current time
     * @return the overstay charge for the reservation
     */
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

    /**
     * Charges a client a specified amount.
     *
     * @param client the client to charge
     * @param amount the amount to charge
     */
    private void chargeClient(Client client, double amount) {
        sendNotification(client, "Charged: $" + amount);
    }

    /**
     * Sends a notification to a client.
     *
     * @param client the client to send the notification to
     * @param message the message to send
     */
    private void sendNotification(Client client, String message) {
        System.out.println("Notification to " + client.getPhoneNumber() + ": " + message);
    }

    /**
     * Checks if the station is available during the specified time.
     *
     * @param reservation the reservation to check availability for
     * @param currentTime the current time
     * @return true if the station is available, false otherwise
     */
    private boolean isStationAvailable(Reservation reservation, LocalDateTime currentTime) {
        for (Reservation res : reservations) {
            if (res != reservation && res.startTime.isBefore(currentTime.plusMinutes(10)) && res.endTime.isAfter(currentTime)) {
                return false;
            }
        }
        return true;
    }
}
