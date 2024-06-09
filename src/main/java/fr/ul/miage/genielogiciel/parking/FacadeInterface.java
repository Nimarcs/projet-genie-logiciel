package fr.ul.miage.genielogiciel.parking;

public class FacadeInterface {

    private ClientList clients;

    private ChargingStationList chargingStations;

    private ReservationList reservationList;

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

    /**
     * Check if someone is connected
     * @return true if someone is connected, else false
     */
    public boolean isConnected() {
        return currentClient != null;
    }
}
