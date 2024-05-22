package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class ChargingStation {

    // === General info of charging station ===
    private int idStation;
    private boolean isDisponible;

    ChargingStation(int idStation, boolean isDisponible) {
        setIdStation(idStation);
        setDispobnible(isDisponible);
    }

    //    === GETTERS and SETTERS ===
    public int getIdStation() { return idStation; }
    public void setIdStation(int id) { this.idStation = idStation; }


    public boolean getDisponible() { return isDisponible; }
    public void setDispobnible(boolean disponible) { isDisponible = disponible; }

    List<Reservation> reservations = new ArrayList<>();

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
                sendNotification(client, "Checked in successfully.");
                return;
            }
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
                    sendNotification(client, "Checked out successfully. Charged for the full reservation period and overstay charge of $" + overstayCharge);
                } else {
                    chargeClient(client, calculateNormalCharge(reservation));
                    sendNotification(client, "Checked out successfully. Charged for the full reservation period.");
                }
                reservation.cancelReservation();
//                sendNotification(client, "Checked out successfully.");

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
                offerExtension(reservation, currentTime);
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
        // Charging logic, e.g., process payment using debitCardNumber
        sendNotification(client, "Charged: $" + amount);
    }

    private void sendNotification(Client client, String message) {
        // Send notification logic, e.g., SMS or email
        System.out.println("Notification to " + client.getPhoneNumber() + ": " + message);
    }
    private void offerExtension(Reservation reservation, LocalDateTime currentTime) {
        // Check if the station is available for extension
        if (isStationAvailable(reservation, currentTime)) {
            sendNotification(reservation.client, "Would you like to extend your reservation? Additional charges apply.");
            // Logic to handle client's response and process the extension
        }
    }

    private boolean isStationAvailable(Reservation reservation, LocalDateTime currentTime) {
        // Check if the station is available for the period after the waiting time
        for (Reservation res : reservations) {
            if (res != reservation && res.startTime.isBefore(currentTime.plusMinutes(10)) && res.endTime.isAfter(currentTime)) {
                return false;
            }
        }
        return true;
    }




    public static void main(String[] args) {
//        test
        Client client = new Client("chel","dds","dfd","+330000","sd@sjdn","121","aa123a" );

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);

        Reservation reservation = new Reservation(client, startTime, endTime, 10.0, 20.0);

        ChargingStation manager = new ChargingStation(1,true);
        manager.addReservation(reservation);


        //  client not showing up within the reserved slot and waiting period
        LocalDateTime currentTime = startTime.plusMinutes(11);
        manager.processNoShowReservations(currentTime);

        //  client overstaying
        currentTime = endTime.plusHours(2);
        manager.checkOut(client, currentTime);


    }

}