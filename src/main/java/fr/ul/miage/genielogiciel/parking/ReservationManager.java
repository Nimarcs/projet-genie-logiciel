package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class ReservationManager extends ArrayList<Reservation> {

    private final ClientNotifier clientNotifier = new ClientNotifier();
    private final ClientCharger clientCharger = new ClientCharger();

    /**
     * Adds a reservation to the charging station and confirms it
     *
     * @param reservation the reservation to add
     */
    public void addReservation(ChargingStation station, Reservation reservation) {
        this.add(reservation);
        reservation.confirmReservation();
        clientNotifier.sendNotification(reservation.client, "Reservation confirmed for " + reservation.startTime);
        station.getReservations().add(reservation);
    }

    /**
     * Checks in a client to their reservation if within the waiting period.
     *
     * @param client the client to check in
     * @param currentTime the current time
     */
    public void checkIn(ChargingStation station, Client client, LocalDateTime currentTime) {
        for (Reservation reservation : this) {
            if (reservation.client.equals(client) && !reservation.isNoShow &&
                    currentTime.isAfter(reservation.startTime) && currentTime.isBefore(reservation.startTime.plusMinutes(10))) {
                reservation.confirmReservation();
                double chargeAmount = clientCharger.calculateNormalCharge(reservation);
                clientCharger.chargeClient(client, chargeAmount);
                clientNotifier.sendNotification(client, "Checked in successfully. Charged for the full reservation period.");
                return;
            }
            reservation.cancelReservation();
        }
        clientNotifier.sendNotification(client, "Check-in failed or reservation not found.");
    }

    /**
     * Checks out a client from their reservation and handles overstay charges if applicable
     *
     * @param client the client to check out
     * @param currentTime the current time
     */
    public void checkOut(ChargingStation station, Client client, LocalDateTime currentTime) {
        for (Reservation reservation : this) {
            if (reservation.client.equals(client) && reservation.isConfirmed) {
                double chargeAmount;
                if (currentTime.isAfter(reservation.endTime)) {
                    reservation.isOverstayed = true;
                    chargeAmount = clientCharger.calculateOverstayCharge(reservation, currentTime);
                    clientNotifier.sendNotification(client, "Charged for the full reservation period and overstay charge of $" + chargeAmount);
                } else {
                    chargeAmount = clientCharger.calculateNormalCharge(reservation);
                    clientNotifier.sendNotification(client, "Charged for the full reservation period.");
                }
                clientCharger.chargeClient(client, chargeAmount);
                reservation.cancelReservation();
                clientNotifier.sendNotification(client, "Checked out successfully.");
                return;
            }
        }
        clientNotifier.sendNotification(client, "Check-out failed or reservation not found.");
    }

    /**
     * Processes reservations where the client did not show up within the waiting period
     *
     * @param currentTime the current time
     */
    public void processNoShowReservations(LocalDateTime currentTime) {
        for (Reservation reservation : this) {
            if (currentTime.isAfter(reservation.startTime.plusMinutes(10))) {
                reservation.markNoShow();
                double chargeAmount = clientCharger.calculateNormalCharge(reservation);
                clientCharger.chargeClient(reservation.client, chargeAmount);
                clientNotifier.sendNotification(reservation.client, "You did not show up. Charged for the full reservation period of $" + chargeAmount);
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
    public void offerExtension(ChargingStation station, Client client, LocalDateTime currentTime) {
        for (Reservation reservation : this) {
            if (reservation.client.equals(client) && currentTime.isAfter(reservation.startTime) && currentTime.isBefore(reservation.startTime.plusMinutes(10))) {
                if (station.isStationAvailable(reservation, currentTime)) {
                    clientNotifier.sendNotification(reservation.client, "Would you like to extend your reservation? Additional charges apply.");
                }
            }
        }
    }

    /**
     * Handles a late arrival of a client.
     *
     * @param client the client who arrived late
     * @param currentTime the current time
     */
    public void handleLateArrival(ChargingStation station, Client client, LocalDateTime currentTime, Scanner input) {
        for (Reservation reservation : this) {
            if (reservation.client.equals(client) && currentTime.isAfter(reservation.startTime.plusMinutes(10))) {
                System.out.println("You have arrived beyond the waiting period after the start of your booked period.");

                System.out.print("Please enter how many hours you plan to stay: ");
                int additionalHours = input.nextInt();
                input.nextLine();

                LocalDateTime newEndTime = currentTime.plusHours(additionalHours);

                if (station.isStationAvailableDuringInterval(currentTime, newEndTime)) {
                    reservation.setEndTime(newEndTime);
                    double newChargeAmount = clientCharger.calculateNormalCharge(reservation);
                    clientCharger.chargeClient(client, newChargeAmount);
                    clientNotifier.sendNotification(client, "Your vehicle has been recharged from the beginning of your initial reservation until the end of your newly booked slot.");
                } else {
                    clientNotifier.sendNotification(client, "No available charging stations during your desired interval.");
                }

                return;
            }
        }
        clientNotifier.sendNotification(client, "Late arrival handling failed or reservation not found.");
    }
}
