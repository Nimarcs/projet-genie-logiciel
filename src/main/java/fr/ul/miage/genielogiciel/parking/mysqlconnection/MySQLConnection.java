package fr.ul.miage.genielogiciel.parking.mysqlconnection;

import fr.ul.miage.genielogiciel.parking.ChargingStation;
import fr.ul.miage.genielogiciel.parking.Client;
import fr.ul.miage.genielogiciel.parking.DatabaseConnection;
import fr.ul.miage.genielogiciel.parking.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class MySQLConnection implements DatabaseConnection {
    private static final Logger LOG = Logger.getLogger(MySQLConnection.class.getName());

    private MySQLBridge mySQLBridge;


    public MySQLConnection() {

        //Set of the bridge
        mySQLBridge = new MySQLBridge();
        mySQLBridge.initConnection();
        if (!mySQLBridge.isConnectionSet()) {
            LOG.severe("Failed connecting to MySQL");
            System.exit(1);
        }


    }

    /**
     * Give back the reservation of the client at the time given
     * May give back null if no reservation was made at that time
     *
     * @param client   client that made the reservation
     * @param dateTime time for which the reservation is made
     * @return reservation at that time or null
     */
    @Override
    public Reservation getReservation(Client client, LocalDateTime dateTime) {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }

    /**
     * Give back the reservation of the ChargingStation at that time
     * May give back null if the charging station is free at that time
     *
     * @param chargingStation charging station that was supposedly reserved
     * @param dateTime        time at which the charging station was reserved
     * @return reservation that booked the charging station or null
     */
    @Override
    public Reservation getReservation(ChargingStation chargingStation, LocalDateTime dateTime) {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }

    /**
     * Client linked to the reservation
     *
     * @param reservation reservation to which the client is linked
     * @return Client liked to the reservation
     */
    @Override
    public Client getClient(Reservation reservation) {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }

    /**
     * Change the charging station of a reservation to the new one
     *
     * @param reservation        Reservation to change
     * @param newChargingStation new charging station that will replace the old one
     */
    @Override
    public void changeChargingStation(Reservation reservation, ChargingStation newChargingStation) {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }

    /**
     * Free the charging Station of it's reservation
     * The reservation cost doesn't change
     *
     * @param chargingStation charging station to free up
     */
    @Override
    public void freeChargingStation(ChargingStation chargingStation) {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }

    /**
     * Update the reservation with new data
     * May have problems with synchronicity i need to look into it
     *
     * @param reservation reservation to update
     */
    @Override
    public void updateReservation(Reservation reservation) {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }

    /**
     * Get ALL the reservation of a client in a time frame
     * If the reservation end in the time frame it is counted as valid no matter when it started
     *
     * @param client client that made the reservation
     * @param begin  beginning of the time frame
     * @param end    end of the time frame
     * @return List of all the reservation made
     */
    @Override
    public List<Reservation> getReservationsOfClient(Client client, LocalDateTime begin, LocalDateTime end) {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }

    /**
     * Get ALL the available station currently
     *
     * @return List of all currently available charging station
     */
    @Override
    public List<ChargingStation> getAvailiblesChargingStations() {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }

    /**
     * Get all client with reservation inside the timeframe
     * If the reservation end in the time frame it is counted as valid no matter when it started
     *
     * @param begin beginning of the time frame
     * @param end   end of the time frame
     * @return List of all active client
     */
    @Override
    public List<Client> getActiveClient(LocalDateTime begin, LocalDateTime end) {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }

    /**
     * Get the charging station from the database
     *
     * @param chargingStation old charging station to obtain again from the database
     * @return charging station from the database
     */
    @Override
    public ChargingStation getChargingStation(ChargingStation chargingStation) {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }

    /**
     * Get the reservation from the database
     *
     * @param reservation old reservation to obtain again from the database
     * @return reservation from the database
     */
    @Override
    public Reservation getReservation(Reservation reservation) {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }

    /**
     * Get the client from the database
     *
     * @param client old client to obtain again from the database
     * @return client from the database
     */
    @Override
    public Client getClient(Client client) {
        throw new IllegalStateException("Functionnality not done yet");
        //TODO correctly do the fonctionnality
    }
}
