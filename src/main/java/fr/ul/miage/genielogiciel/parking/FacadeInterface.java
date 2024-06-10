package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class FacadeInterface {

    private ClientList clients;

    private ChargingStationList chargingStations;

    private ReservationList reservationList;

    private ReservationManager reservationManager;

    /**
     * Current client, should be delegated to a connexion specific class inside the business
     */
    private Client currentClient;

    /**
     * Constructor of FacadeInterface
     * Create a totally empty parking
     */
    public FacadeInterface(){
        clients = new ClientList();
        chargingStations = new ChargingStationList();
        reservationList = new ReservationList();
        reservationManager = new ReservationManager();
        currentClient = null;
    }

    /**
     * Method that initialize the parking with default values
     * Reset the current values beforehand
     */
    public void initializeParking(){
        //TODO ClientList and ChargingStationList could have a clear function
        clients = new ClientList();
        chargingStations = new ChargingStationList();
        reservationList = new ReservationList();

        //set default values
        Client client1 = new Client();
        Client client2 = new Client("Lara", "Mara", "39 rue Paris, 54000 Nancy", "+33785546942", "test@gmail.com", "00012342", "LICENSE123", "test", "test2test");

        clients.addClient(client1);
        clients.addClient(client2);

        chargingStations.addStation(new ChargingStation(123, true));
        chargingStations.addStation(new ChargingStation(456, true));
        chargingStations.addStation(new ChargingStation(789, false));
        chargingStations.addStation(new ChargingStation(12, false));

    }

    /**
     * Return the client with the corresponding username or null
     * @param username username of the client
     * @return Client or null
     */
    public Client findClientByUsername(String username) {
        return clients.findClientByUsername(username);
    }

    /**
     * Return the client with the corresponding licenseNumber or null
     * @param licenseNumber licenseNumber of the client
     * @return Client or null
     */
    public Client findClientByLicense(String licenseNumber) {
        return clients.findClientByLicense(licenseNumber);
    }

    /**
     * Return the client with the corresponding reservationNumber or null
     * @param reservationNumber reservationNumber of the client
     * @return Client or null
     */
    public Client findClientByReservationNumber(String reservationNumber) {
        return clients.findClientByReservationNumber(reservationNumber);
    }

    /**
     * Return charging station that the client given use or null
     * @param client client given
     * @return Charging station or null
     */
    public ChargingStation findChargingStationByClient(Client client) {
        return chargingStations.findChargingStationByClient(client);
    }

    /**
     * Return the Reservation of the client or null if not found
     * @param client client given
     * @return Reservation or null
     */
    public Reservation findReservationByClient(Client client) {
        return reservationList.findReservationByClient(client);
    }

    /**
     * Return an unmodifiable list of the available station inside the parking
     * @return List of the available station
     */
    public List<ChargingStation> findAvailableStations() {
        return Collections.unmodifiableList(chargingStations.findAvailableStations());
    }

    /**
     * Return the charging station with this id or null
     * @param id id of the charging station
     * @return charging station or null
     */
    public ChargingStation findChargingStationById(int id) {
        return chargingStations.findChargingStation(id);
    }

    public void askExtention(Reservation reservation) {
        reservationManager.askExtention(reservation);
    }

    /**
     * Check out from reservation
     * @param reservation reservation to check out from
     */
    public void checkOutFromReservation(Reservation reservation) {
        //TODO
    }

    /**
     * Check in for the reservation
     * @param reservation reservation to check in
     */
    public void checkInFromReservation(Reservation reservation) {
        //TODO
    }

    /**
     * Check if someone is connected
     * @return true if someone is connected, else false
     */
    public boolean isConnected() {
        return currentClient != null;
    }

    /**
     * Add a new client to the system
     * @param client client to add
     */
    public void addClient(Client client) {
        clients.addClient(client);
    }

    /**
     * Getter of the current Client
     * @return current client of the app
     */
    public Client getCurrentClient() {
        return currentClient;
    }

    /**
     * Set the current connected client
     * @param currentClient currently connected client
     */
    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }
}
