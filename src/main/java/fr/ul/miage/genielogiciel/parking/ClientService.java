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

    /**
     * Prompts the user for input and validates it using the given setter method
     *
     * @param input    the scanner object to read user input
     * @param prompt   the prompt message
     * @param setter   the setter method to validate and set the input
     * @return the validated input
     */
    private String promptAndValidate(Scanner input, String prompt, ValueSetter setter) {
        String userInput;
        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            System.out.print(prompt);
            userInput = input.nextLine().trim();
            try {
                setter.set(userInput);
                return userInput;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                attempts++;
            }
        }
        System.out.println("Too many invalid attempts. Bye!");
        throw new IllegalArgumentException("Too many invalid attempts.");
    }

}
