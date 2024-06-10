package fr.ul.miage.genielogiciel.parking.commandLine;

import fr.ul.miage.genielogiciel.parking.Client;
import fr.ul.miage.genielogiciel.parking.FacadeInterface;

import java.util.Optional;
import java.util.Scanner;

import static fr.ul.miage.genielogiciel.parking.Client.*;

public class ClientDisplayService {

    private final Displayer displayer;
    private final Scanner scanner;

    public ClientDisplayService(Scanner scanner, Displayer displayer) {
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
        String username = validateInput("Username: ", USERNAME_REGEX,
                "Invalid input. Please enter a valid username (letters, numbers, ., _).", 4, 15);

        //If the client is not created
        Optional<Client> optionalClient = facadeInterface.findClientByUsername(username);

        if (optionalClient.isEmpty()){
            displayer.displayErrorMessage("Username unknown. Going back.");
            return false;
        }

        Client client = optionalClient.get();

        //3 tries to write correct password and connect
        for (int i = 0; i < 3; i++) {

            displayer.displayMessage("Password : ");
            String password = scanner.nextLine().trim();

            if (facadeInterface.checkPassword(password, client)) {
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
            client.setName(validateInput("Name: ", NAME_REGEX, "Invalid input. Please enter a valid name without numbers or special characters.", MIN_SIZE_NAME, MAX_SIZE_NAME));
            client.setSurname(validateInput("Surname: ", NAME_REGEX, "Invalid input. Please enter a valid surname without numbers or special characters.", MIN_SIZE_SURNAME, MAX_SIZE_SURNAME));
            client.setAdresse(validateInput("Address: ", ADDRESS_REGEX, "Invalid input. Please enter a valid address without special characters.", MIN_SIZE_ADRESSE, MAX_SIZE_ADRESSE));
            client.setPhoneNumber(validateInput("Phone Number: ", PHONE_NUMBER_REGEX, "Invalid input. Please enter a valid phone number. It may contain only numbers and an optional leading +.", MIN_SIZE_PHONE, MAX_SIZE_PHONE));
            client.setEmail(validateInput("Email: ", EMAIL_REGEX, "Invalid input. Please enter a valid email address.", MIN_SIZE_EMAIL, MAX_SIZE_EMAIL));
            client.setCreditCard(validateInput("Credit Card Number: ", CREDIT_CARD_REGEX, "Invalid input. Please enter a valid credit card number.", SIZE_CREDIT_CARD, SIZE_CREDIT_CARD));
            client.setUsername(validateInput("Username: ", USERNAME_REGEX, "Invalid input. Please enter a valid username (letters, numbers, ., _).", MIN_SIZE_USERNAME, MAX_SIZE_USERNAME));
            client.setPassword(validateInput("Password: ", PASSWORD_REGEX, "Invalid input. Please enter a valid password (letters and numbers only).", MIN_SIZE_PASSWORD, MAX_SIZE_PASSWORD));

            String plateNumbers = validateInput("License Plate Numbers (optional - press enter to skip): ", PLATE_NUMBER_REGEX, "Invalid input. Please enter a valid license plate number.", SIZE_PLATE_NUMBERS, SIZE_PLATE_NUMBERS);
            if (plateNumbers.trim().length() >= 6) {
                client.setPlateNumbers(plateNumbers);
            }
        } catch (IllegalArgumentException e){
            displayer.displayErrorMessage(e.getMessage() + " Going back.");
        }

        displayer.displayMessage("Registration completed successfully!");
        facadeInterface.addClient(client);
    }

    /**
     * Validate an input
     * @param prompt Prompt given to the user to ask him for the input
     * @param regex regex the input must comply to
     * @param errorMessage message displayed in case of a mistake in input
     * @param minLength minimum length of the input
     * @param maxLength maximum length of the input
     * @return the validated input
     * @throws IllegalArgumentException if the input is not valid
     */
    private String validateInput(String prompt, String regex, String errorMessage, int minLength, int maxLength) {
        String userInput;
        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            displayer.displayMessage(prompt);
            userInput = scanner.nextLine().trim();
            if (userInput.length() >= minLength && userInput.length() <= maxLength && userInput.matches(regex)) {
                return userInput;
            } else {
                displayer.displayErrorMessage(errorMessage);
                attempts++;
            }
        }
        throw new IllegalArgumentException("Too many invalid attempts.");
    }
}
