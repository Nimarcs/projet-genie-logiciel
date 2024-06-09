package fr.ul.miage.genielogiciel.parking.commandLine;

import fr.ul.miage.genielogiciel.parking.Client;
import fr.ul.miage.genielogiciel.parking.FacadeInterface;

import java.util.Scanner;
import java.util.Objects;

public class ClientService {

    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]+$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9._]+$";
    private static final String CREDIT_CARD_REGEX = "^[0-9]+$";
    private static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PHONE_NUMBER_REGEX = "^[+]?\\d+$";
    private static final String ADRESS_REGEX = "^[a-zA-Z0-9\\s,]+$";
    private static final String NAME_REGEX = "^[a-zA-Z\\s]+$";

    private final Displayer displayer;
    private final Scanner scanner;

    public ClientService(Scanner scanner, Displayer displayer) {
        this.scanner = scanner;
        this.displayer = displayer;
    }

    /**
     * Display the login form
     * @param facadeInterface facade with the business
     * @return true if the connexion was successful false if not
     */
    public boolean loginForm(FacadeInterface facadeInterface) {
        //Already connected
        if (facadeInterface.getCurrentClient() != null){
            displayer.displayErrorMessage("You are already connected. Going back.");
            return false;
        }

        //Start the form
        displayer.displayHeader("LOGIN FORM");
        displayer.displayMessage("Enter your credentials:");

        //Get username
        // Use a less restrictive regex to capture all alphanumeric usernames
        String username = validInput("Username: ", USERNAME_REGEX,
                "Invalid input. Please enter a valid username (letters, numbers, ., _).", 4, 15);

        //If the client is not created
        Client client = facadeInterface.findClientByUsername(username);
        if (client == null){
            displayer.displayErrorMessage("Username unknown. Going back.");
            return false;
        }

        //3 tries to write correct password and connect
        for (int i = 0; i < 3; i++) {
            String password = validInput("Password: ", PASSWORD_REGEX,
                    "Invalid input. Please enter a valid password (letters and numbers only).", 8, 20);

            if (Objects.equals(client.getPassword(), password)) {
                displayer.displayMessage("Login successful!");
                facadeInterface.setCurrentClient(client);
                return true;
            } else {
                displayer.displayErrorMessage("Invalid password. Please try again. (" + (2 - i) + " attempts remaining)");
            }
        }
        displayer.displayErrorMessage("Too many failed attempts. Login failed.");
        return false;
    }

    /**
     * Display the registration form
     * @param facadeInterface link to the business infos
     */
    public void registrationForm(FacadeInterface facadeInterface) {
        displayer.displayHeader("REGISTRATION FORM");

        displayer.displayMessage("Enter your credentials");

        Client client = new Client();

        try {
            client.setName(validInput("Name: ", NAME_REGEX, "Invalid input. Please enter a valid name without numbers or special characters.", 2, 20));
            client.setSurname(validInput("Surname: ", NAME_REGEX, "Invalid input. Please enter a valid surname without numbers or special characters.", 2, 20));
            client.setAdresse(validInput("Address: ", ADRESS_REGEX, "Invalid input. Please enter a valid address without special characters.", 5, 40));
            client.setPhoneNumber(validInput("Phone Number: ", PHONE_NUMBER_REGEX, "Invalid input. Please enter a valid phone number. It may contain only numbers and an optional leading +.", 10, 12));
            client.setEmail(validInput("Email: ", EMAIL_REGEX, "Invalid input. Please enter a valid email address.", 5, 50));
            client.setCreditCard(validInput("Credit Card Number: ", CREDIT_CARD_REGEX, "Invalid input. Please enter a valid credit card number.", 10, 16));
            client.setUsername(validInput("Username: ", USERNAME_REGEX, "Invalid input. Please enter a valid username (letters, numbers, ., _).", 5, 15));
            client.setPassword(validInput("Password: ", PASSWORD_REGEX, "Invalid input. Please enter a valid password (letters and numbers only).", 8, 20));

            String plateNumbers = validInput("License Plate Numbers (optional - press enter to skip): ", "^[a-zA-Z0-9]+$", "Invalid input. Please enter a valid license plate number.", 0, 12);
            if (plateNumbers.trim().length() >= 6) {
                client.setPlateNumbers(plateNumbers);
            }
        } catch (IllegalArgumentException e){
            displayer.displayErrorMessage(e.getMessage() + " Going back.");
        }

        displayer.displayMessage("Registration completed successfully!");
        facadeInterface.addClient(client);
    }

    private String validInput(String prompt, String regex, String errorMessage, int minLength, int maxLength) {
        String userInput;
        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            System.out.print(prompt);
            userInput = scanner.nextLine().trim();
            if (userInput.length() >= minLength && userInput.length() <= maxLength && userInput.matches(regex)) {
                return userInput;
            } else {
                System.out.println(errorMessage);
                attempts++;
            }
        }
        throw new IllegalArgumentException("Too many invalid attempts.");
    }
}
