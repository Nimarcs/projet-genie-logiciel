package fr.ul.miage.genielogiciel.parking;

import java.util.ArrayList;
import java.util.List;

public class reservationList {


    private final List<Reservation> reservations;

    /**
     * Create a new list of reservations.
     */
    public reservationList() {
        reservations = new ArrayList<>();
    }

    /**
     * Add a reservation to the list
     *
     * @param reservation the reservation to be added
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }



    public Reservation findReservation(int idReservation){
        for (Reservation reservation: reservations){
            if (reservation.getIdStation() == idReservation) return reservation;
        }
        return null;
    }


    /**
     *
     *
     *
     *
     */
    public Reservation findReservationByClient(Client client) {

            for (Reservation reservation : reservations) {
                if (reservation.client.equals(client) && reservation.isConfirmed) {
                    return reservation;
                }

        }
        return null;
    }


}











