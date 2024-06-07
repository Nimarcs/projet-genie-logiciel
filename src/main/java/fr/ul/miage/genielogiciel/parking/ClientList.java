package fr.ul.miage.genielogiciel.parking;
import java.util.List;
import java.util.ArrayList;



/**
 * List of {@link Client} in the parking system
 * <p> Provides methods:
 * <ul>
 *   <li>{@link ClientList#addClient(Client)} - add client to the list</li>
 *   <li>find clients by
 *      <ul>{@link ClientList#findClientByUsername(String)} - username</ul>
 *      <ul>{@link ClientList#findClientByLicense(String)} - license</ul>
 *      <ul>{@link ClientList#findByMobilePhone(String)} - phone number</ul>
 *   </li>
 * </ul>
 *</p>
 *
 *
 * @since 1.0
 */

// TODO Add check if user with the same email and username already exist
public class ClientList {

   private final List<Client> clients;


    /**
     * Create a new client list
     * Initialize an empty list of clients
     */
   public ClientList () {
       clients = new ArrayList<>();
   }


    /**
     * Add a client to the list
     *
     * @param client the client to be added
     */
   public void addClient(Client client) {
       clients.add(client);
   }


    /**
     * Find a client by their username.
     * (Using for login form)
     *
     * @param username the username to search for
     * @return the client with the specified username, or null if not found
     * @see Client#getUsername()
     */
   public Client findClientByUsername(String username){
       for (Client client: clients){
           if (client.getUsername().equals(username)) return client;
       }
       return null;
   }


    /**
     * Find a client by their license plate number.
     *
     * @param license the license plate number to search for
     * @return the client with the specified license plate number, or null if not found
     * @see Client#getPlateNumbers()
     */
    public Client findClientByLicense(String license){
        for (Client client: clients){
            if (client.getPlateNumbers().equals(license)) return client;
        }
        return null;
    }


    /**
     * Find a client by their mobile phone number.
     *
     * @param phoneNumber the phone number to search for
     * @return the client with the specified phone number, or null if not found
     * @see Client#getPhoneNumber()
     */

    public Client findByMobilePhone(String phoneNumber) {
        for (Client client : clients) {
            if (client.getPhoneNumber().equals(phoneNumber)) {
                return client;
            }
        }
        return null;
    }

    public Client findClientByReservationNumber(String reservNumber) {
        for (Client client : clients) {
            if (client.getPhoneNumber().equals(reservNumber)) {
                return client;
            }
        }
        return null;

    }

}
