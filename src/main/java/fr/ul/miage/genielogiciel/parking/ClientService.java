package fr.ul.miage.genielogiciel.parking;

import java.util.*;

public class ClientService {

    /**
     * Checks the login credentials and returns an Optional<Client>
     *
     * @param username the username to search for
     * @param clients  the list of clients
     * @return an Optional<Client> if a matching client is found, otherwise Optional.empty()
     */
    public Optional<Client> findByUsername(String username, Collection<Client> clients) {
        return clients.stream()
                .filter(client -> username.equals(client.getUsername())) // Updated to handle null
                .findFirst();
    }

    /**
     * Checks the password by comparing it with the client's password
     *
     * @param password the password to validate
     * @param client   the client object to compare the password with
     * @return true if the password matches, false otherwise
     */
    public boolean checkPassword(String password, Client client) {
        return password.matches("^[a-zA-Z0-9]+$") && Objects.equals(client.getPassword(), password);
    }


}
