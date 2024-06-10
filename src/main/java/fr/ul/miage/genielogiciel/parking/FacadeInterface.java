package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.util.*;

public class FacadeInterface {

    private Collection<Client> clients;

    private Collection<ChargingStation> chargingStations;

    private Collection<Reservation> reservations;

    private ClientService clientService;

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
        clients = new HashSet<>();
        chargingStations = new HashSet<>();
        reservations = new HashSet<>();
        clientService = new ClientService();
        reservationManager = new ReservationManager();
        currentClient = null;
    }

    /**
     * Method that initialize the parking with default values
     * Reset the current values beforehand
     */
    public void initializeParking(){
        //TODO ClientList and ChargingStationList could have a clear function
        clients = new HashSet<>();
        chargingStations = new HashSet<>();
        reservations = new HashSet<>();

        Client client1 = new Client();
        Client client2 = new Client("Lara", "Mara", "39 rue Paris, 54000 Nancy", "+33785546942", "test@gmail.com", "0001234223415435", "LIC123", "test", "test2test");

        // Create lists
        clients.add(client1);
        clients.add(client2);

        // Create charging station list
        chargingStations.add(new ChargingStation(123456, true));
        chargingStations.add(new ChargingStation(456321, true));
        chargingStations.add(new ChargingStation(789752, false));
        chargingStations.add(new ChargingStation(125632, false));

        // Define reservation times
        LocalDateTime startTime = LocalDateTime.of(2023, 6, 10, 10, 0); // Example start time
        LocalDateTime endTime = LocalDateTime.of(2023, 6, 10, 12, 0);

        // Create reservation list
        reservations.add(new Reservation(client2, startTime, endTime));


    }

    /**
     * Return the client with the corresponding username or null
     * @param username username of the client
     * @return Client or null
     */
    public Optional<Client> findClientByUsername(String username) {
        return clientService.findByUsername(username, clients);
    }

    /**
     * Check if the password is correct one for the client
     * @param password password of the client
     * @param client client that is supposed to have that password
     * @return true if the password is correct, else false
     */
    public boolean checkPassword(String password, Client client){
        return clientService.checkPassword(password, client);
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
        return reservations.findReservationByClient(client);
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

    /**
     * Return the client with this phone number or null
     * @param mobileNumber mobile number given
     * @return client with the phone number or null
     */
    public Client findByMobilePhone(String mobileNumber) {
        return clients.findByMobilePhone(mobileNumber);
    }

    /**
     * Ask for an extention of the waiting time
     * @param reservation reservation to which we want to ask for an extention
     */
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

    public void addReservation(ChargingStation selectedStation, Reservation reservation) {
        reservationManager.addReservation(selectedStation, reservation);
    }
}
