package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.util.List;

public interface DatabaseConnection {

    /**
     * Give back the reservation of the client at the time given
     * May give back null if no reservation was made at that time
     *
     * @param client   client that made the reservation
     * @param dateTime time for which the reservation is made
     * @return reservation at that time or null
     */
    Reservation getReservation(Client client, LocalDateTime dateTime);

    /**
     * Give back the reservation of the ChargingStation at that time
     * May give back null if the charging station is free at that time
     *
     * @param chargingStation charging station that was supposedly reserved
     * @param dateTime        time at which the charging station was reserved
     * @return reservation that booked the charging station or null
     */
    Reservation getReservation(ChargingStation chargingStation, LocalDateTime dateTime);

    /**
     * Client linked to the reservation
     *
     * @param reservation reservation to which the client is linked
     * @return Client liked to the reservation
     */
    Client getClient(Reservation reservation);

    /**
     * Change the charging station of a reservation to the new one
     *
     * @param reservation        Reservation to change
     * @param newChargingStation new charging station that will replace the old one
     */
    void changeChargingStation(Reservation reservation, ChargingStation newChargingStation);

    /**
     * Free the charging Station of it's reservation
     * The reservation cost doesn't change
     *
     * @param chargingStation charging station to free up
     */
    void freeChargingStation(ChargingStation chargingStation);

    /**
     * Update the reservation with new data
     * May have problems with synchronicity i need to look into it
     *
     * @param reservation reservation to update
     */
    void updateReservation(Reservation reservation);

    /**
     * Get ALL the reservation of a client in a time frame
     * If the reservation end in the time frame it is counted as valid no matter when it started
     *
     * @param client client that made the reservation
     * @param begin  beginning of the time frame
     * @param end    end of the time frame
     * @return List of all the reservation made
     */
    List<Reservation> getReservationsOfClient(Client client, LocalDateTime begin, LocalDateTime end);

    /**
     * Get ALL the available station currently
     *
     * @return List of all currently available charging station
     */
    List<ChargingStation> getAvailiblesChargingStations();

    /**
     * Get all client with reservation inside the timeframe
     * If the reservation end in the time frame it is counted as valid no matter when it started
     *
     * @param begin beginning of the time frame
     * @param end   end of the time frame
     * @return List of all active client
     */
    List<Client> getActiveClient(LocalDateTime begin, LocalDateTime end);

    /**
     * Get the charging station from the database
     *
     * @param chargingStation old charging station to obtain again from the database
     * @return charging station from the database
     */
    ChargingStation getChargingStation(ChargingStation chargingStation);

    /**
     * Get the reservation from the database
     *
     * @param reservation old reservation to obtain again from the database
     * @return reservation from the database
     */
    Reservation getReservation(Reservation reservation);

    /**
     * Get the client from the database
     *
     * @param client old client to obtain again from the database
     * @return client from the database
     */
    Client getClient(Client client);


}
