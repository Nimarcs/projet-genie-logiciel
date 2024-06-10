package fr.ul.miage.genielogiciel.parking;

import java.time.LocalDateTime;
import java.util.*;

public class FacadeInterface {

    private Collection<Client> clients;

    private Collection<ChargingStation> chargingStations;

    private Collection<Reservation> reservations;

    private final ClientService clientService;

    private final ReservationManager reservationManager;

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
     * Initialize the parking with specific values
     * @param chargingStations Collection of charging station of the parking
     * @param clients Collection of clients of the parking
     * @param reservations Collection of reservation of the parking
     */
    public void initializeParking(Collection<ChargingStation> chargingStations, Collection<Client> clients,Collection<Reservation> reservations) {
        this.chargingStations = chargingStations;
        this.clients = clients;
        this.reservations = reservations;
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
     * Find available charging stations.
     *
     * @return the list of available charging stations
     */
    public Collection<ChargingStation> findAvailableStations() {
        Collection<ChargingStation> availableStations = new ArrayList<>();
        for (ChargingStation station : chargingStations) {
            if (station.getDisponible()) {
                availableStations.add(station);
            }
        }
        return availableStations;
    }

    /**
     * Find a charging station by its ID.
     *
     * @param idStation the ID of the charging station
     * @return an optional containing the charging station if found, otherwise empty
     */
    public Optional<ChargingStation> findChargingStation(int idStation) {
        return chargingStations.stream()
                .filter(station -> station.getIdStation() == idStation)
                .findFirst();
    }

    /**
     * Find a client by their license number.
     *
     * @param licenseNumber the license number of the client
     * @return an optional containing the client if found, otherwise empty
     */
    public Optional<Client> findClientByLicense(String licenseNumber) {
        return clients.stream()
                .filter(client -> client.getPlateNumbers().equals(licenseNumber))
                .findFirst();
    }

    /**
     * Find a reservation by a client.
     *
     * @param client the client whose reservation is to be found
     * @return an optional containing the reservation if found, otherwise empty
     */
    public Optional<Reservation> findReservationByClient(Client client) {
        return reservations.stream()
                .filter(reservation -> reservation.client.equals(client) && reservation.isConfirmed)
                .findFirst();
    }

    /**
     * Find a client by their mobile phone number.
     *
     * @param phone the phone number of the client
     * @return an optional containing the client if found, otherwise empty
     */
    public Optional<Client> findClientByPhone(String phone) {
        return clients.stream()
                .filter(client -> client.getPhoneNumber().equals(phone))
                .findFirst();
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
        clients.add(client);
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
        reservations.add(reservation);
        reservationManager.addReservation(selectedStation, reservation);
    }
}
