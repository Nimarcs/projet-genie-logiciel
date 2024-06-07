package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ClientCharger {


    /**
     * Charges a client a specified amount.
     *
     * @param client the client to charge
     * @param amount the amount to charge
     */
    public void chargeClient(Client client, double amount) {
        // TODO add logic to charge client if needed
        System.out.println("Charged: $" + amount);
    }


    /**
     * Calculates the normal charge for a reservation.
     *
     * @param reservation the reservation to calculate the charge for
     * @return the normal charge for the reservation
     */
    public double calculateNormalCharge(Reservation reservation) {
        long duration = reservation.startTime.until(reservation.endTime, ChronoUnit.HOURS);
        return duration * reservation.normalRate;
    }


    /**
     * Calculates the overstay charge for a reservation.
     *
     * @param reservation the reservation to calculate the overstay charge for
     * @param currentTime the current time
     * @return the overstay charge for the reservation
     */
    public double calculateOverstayCharge(Reservation reservation, LocalDateTime currentTime) {
        long overstayDuration = reservation.endTime.until(currentTime, ChronoUnit.HOURS);
        double totalOverstayCharge = 0.0;
        double currentOverstayRate = reservation.overstayRate;

        for (int i = 0; i < overstayDuration; i++) {
            totalOverstayCharge += currentOverstayRate;
            currentOverstayRate *= 1.5;
        }

        return calculateNormalCharge(reservation) + totalOverstayCharge;
    }


}
