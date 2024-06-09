package fr.ul.miage.genielogiciel.parking;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.Objects;

public class ClientService {

    private static final String LINE_OF_DASH = "-------------------------";

    /**
     * Handles the login form for the client
     *
     * @param input  the scanner object to read user input
     * @param clients the list of client objects containing login credentials
     * @return true if login is successful, false otherwise
     */
    public boolean loginForm(Scanner input, ArrayList<Client> clients) {
        System.out.println("\n" + LINE_OF_DASH);
        System.out.println("        LOGIN FORM         ");
        System.out.println(LINE_OF_DASH);

        System.out.println("Enter your credentials:");

        Client client = null;
        for (int i = 0; i < 3; i++) {
            String username = input.nextLine();

            Optional<Client> clientOpt = checkLogin(username, clients);

            if (clientOpt.isPresent()) {
                client = clientOpt.get();
                break;
            } else {
                System.out.println("Invalid username. Please try again. (" + (2 - i) + " attempts remaining)");
            }
        }

        if (client == null) {
            System.out.println("Too many invalid attempts. Login failed.");
            return false;
        }

        for (int i = 0; i < 3; i++) {
            System.out.print("Password: ");
            String password = input.nextLine();
            if (checkPassword(password, client)) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid password. Please try again. (" + (2 - i) + " attempts remaining)");
            }
        }

        System.out.println("Too many failed attempts. Login failed.");
        return false;
    }


    /**
     * Checks the login credentials and returns an Optional<Client>
     *
     * @param username the username to search for
     * @param clients  the list of clients
     * @return an Optional<Client> if a matching client is found, otherwise Optional.empty()
     */
    private Optional<Client> checkLogin(String username, ArrayList<Client> clients) {
        return clients.stream()
                .filter(client -> client.getUsername().equals(username))
                .findFirst();
    }

    /**
     * Checks the password by comparing it with the client's password
     *
     * @param password the password to validate
     * @param client   the client object to compare the password with
     * @return true if the password matches, false otherwise
     */
    private boolean checkPassword(String password, Client client) {
        return password.matches("^[a-zA-Z0-9]+$") && Objects.equals(client.getPassword(), password);
    }


    /**
     * Handles the registration form for the client
     *
     * @param input  the scanner object to read user input
     * @param client the client object to register
     */
    public void registrationForm(Scanner input, Client client) {
        System.out.println("\n" + LINE_OF_DASH);
        System.out.println("     REGISTRATION FORM     ");
        System.out.println(LINE_OF_DASH);

        System.out.println("Enter your credentials");

        promptAndValidate(input, "Name: ", client::setName);
        promptAndValidate(input, "Surname: ", client::setSurname);
        promptAndValidate(input, "Address: ", client::setAdresse);
        promptAndValidate(input, "Phone Number: ", client::setPhoneNumber);
        promptAndValidate(input, "Email: ", client::setEmail);
        promptAndValidate(input, "Credit Card Number: ", client::setCreditCard);
        promptAndValidate(input, "License Plate Numbers (optional - press enter to skip): ", client::setPlateNumbers);
        promptAndValidate(input, "Username: ", client::setUsername);
        promptAndValidate(input, "Password: ", client::setPassword);

        System.out.println("Registration completed successfully!");
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

    // 1 - [TO MARCUS]
    // I remade the registration form and don't want to check if the input is okay again
    // (we've already did it in Client class).
    // So I want to use it and call setters from Client
    @FunctionalInterface
    public interface ValueSetter {
        void set(String value) throws IllegalArgumentException;
    }
}
