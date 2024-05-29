package fr.ul.miage.genielogiciel.parking;

import java.util.Scanner;
import java.util.Objects;

public class ClientService {

    public boolean loginForm(Scanner input, Client client) {
        System.out.println("\n-------------------------");
        System.out.println("        LOGIN FORM         ");
        System.out.println("-------------------------");

        System.out.println("Enter your credentials:");
        System.out.println(client.getUsername());
        String username = validInput(input, "Username: ", "^[a-zA-Z0-9._]+$", "Invalid input. Please enter a valid username (letters, numbers, ., _).", 4, 15);




        if (Objects.equals(client.getUsername(), username)) {
            for (int i = 0; i < 3; i++) {
                String password = validInput(input, "Password: ", "^[a-zA-Z0-9]+$", "Invalid input. Please enter a valid password (letters and numbers only).", 8, 20);
                if (Objects.equals(client.getPassword(), password)) {
                    System.out.println("Login successful!");
                    return true;
                } else {
                    System.out.println("Invalid password. Please try again. (" + (2 - i) + " attempts remaining)");
                }
            }
            System.out.println("Too many failed attempts. Login failed.");
        } else {
            System.out.println("Invalid username. Please try again.");
        }
        return false;
    }

    public void registrationForm(Scanner input, Client client) {
        System.out.println("\n-------------------------");
        System.out.println("     REGISTRATION FORM     ");
        System.out.println("-------------------------");

        System.out.println("Enter your credentials");

        client.setName(validInput(input, "Name: ", "^[a-zA-Z\\s]+$", "Invalid input. Please enter a valid name without numbers or special characters.", 2, 20));
        client.setSurname(validInput(input, "Surname: ", "^[a-zA-Z\\s]+$", "Invalid input. Please enter a valid surname without numbers or special characters.", 2, 20));
        client.setAdresse(validInput(input, "Address: ", "^[a-zA-Z0-9\\s,]+$", "Invalid input. Please enter a valid address without special characters.", 5, 40));
        client.setPhoneNumber(validInput(input, "Phone Number: ", "^[+]?\\d+$", "Invalid input. Please enter a valid phone number. It may contain only numbers and an optional leading +.", 10, 12));
        client.setEmail(validInput(input, "Email: ", "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", "Invalid input. Please enter a valid email address.", 5, 50));
        client.setCreditCard(validInput(input, "Credit Card Number: ", "^[0-9]+$", "Invalid input. Please enter a valid credit card number.", 10, 16));
        String plateNumbers = validInput(input, "License Plate Numbers (optional - press enter to skip): ", "^[a-zA-Z0-9]+$", "Invalid input. Please enter a valid license plate number.", 0, 12);
        if (!plateNumbers.trim().isEmpty() && plateNumbers.length() >= 6) {
            client.setPlateNumbers(plateNumbers);
        }
        client.setUsername(validInput(input, "Username: ", "^[a-zA-Z0-9]+$", "Invalid input. Please enter a valid username (letters, numbers, ., _).", 5, 15));
        client.setPassword(validInput(input, "Password: ", "^[a-zA-Z0-9]+$", "Invalid input. Please enter a valid password (letters and numbers only).", 8, 20));

        System.out.println("Registration completed successfully!");
    }

    private String validInput(Scanner input, String prompt, String regex, String errorMessage, int minLength, int maxLength) {
        String userInput;
        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            System.out.print(prompt);
            userInput = input.nextLine().trim();
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
