package fr.ul.miage.genielogiciel.parking;

public class FacadeInterface {

    private ClientList clients;

    private ChargingStationList chargingStations;

    private ReservationList reservationList;

    /**
     * Constructor of FacadeInterface
     * Create a totally empty parking
     */
    public FacadeInterface(){
        clients = new ClientList();
        chargingStations = new ChargingStationList();
        reservationList = new ReservationList();
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


}
