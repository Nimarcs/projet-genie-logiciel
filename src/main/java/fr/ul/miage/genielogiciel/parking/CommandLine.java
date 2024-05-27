package fr.ul.miage.genielogiciel.parking;

import java.util.Objects;
import java.util.Scanner;


public class CommandLine {

    private static Scanner scanner;
    private static ClientList clients = new ClientList();


    public void run() {
        scanner = new Scanner(System.in);
        System.out.println("Welcome to the FastBorne!");
        int userChoice;
        boolean successLogin;
        Client client1 = new Client();

        do {
            userChoice = welcomeMenu(scanner);

            switch (userChoice) {
                case 1:
                    successLogin = loginForm(scanner, client1);
                    if (successLogin) {
                        mainMenu(scanner);
                    }
                    break;
                case 2:
                    registrationForm(scanner, client1);
                    break;
                case 3:
                    System.out.println("--- Bye! ---");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } while (userChoice != 3);
        scanner.close();
    }

    public int welcomeMenu(Scanner input) {
        int selection = -1;

        System.out.println("\n-------------------------");
        System.out.println("          WELCOME         ");
        System.out.println("-------------------------");
        System.out.println("1 - Login");
        System.out.println("2 - Create Account");
        System.out.println("3 - Quit");

        System.out.print("Enter the number of your choice: ");

        while (true) {
            System.out.print("Enter the number of your choice: ");
            if (input.hasNextInt()) {
                selection = input.nextInt();
                if (selection >= 1 && selection <= 3) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
        }

        return selection;
    }

    public boolean loginForm(Scanner input, Client client) {
        System.out.println("\n-------------------------");
        System.out.println("        LOGIN FORM         ");
        System.out.println("-------------------------");

        System.out.println("Enter your credentials:");

        String username = validInput(input, "Username: ", "^[a-zA-Z0-9._]+$", "Invalid input. Please enter a valid username (letters, numbers, ., _).", 5, 15);




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

        // can have only letters
        client.setName(validInput(input, "Name: ", "^[a-zA-Z\\s]+$", "Invalid input. Please enter a valid name without numbers or special characters.", 2, 20));
        client.setSurname(validInput(input, "Surname: ", "^[a-zA-Z\\s]+$", "Invalid input. Please enter a valid surname without numbers or special characters.", 2, 20));

        // can have letters and numbers
        client.setAdresse(validInput(input, "Address: ", "^[a-zA-Z0-9\\s,]+$", "Invalid input. Please enter a valid address without special characters.", 5, 40));

        // can have numbers and symbol +
        client.setPhoneNumber(validInput(input, "Phone Number: ", "^[+]?\\d+$", "Invalid input. Please enter a valid phone number. It may contain only numbers and an optional leading +.", 10, 12));

        // can have numbers, letters and symbols @, ., -
        client.setEmail(validInput(input, "Email: ", "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", "Invalid input. Please enter a valid email address.", 5, 50));

        // Credit Card Number
        String creditCard = validInput(input, "Credit Card Number: ", "^[0-9]+$", "Invalid input. Please enter a valid credit card number.", 10, 16);
        client.setCreditCard(Integer.parseInt(creditCard));

        // License Plate Numbers (optional)
        String plateNumbers = validInput(input, "License Plate Numbers (optional - press enter to skip): ", "^[a-zA-Z0-9\\s,]*$", "Invalid input. Please enter a valid license plate number.", 0, 12);
        if (!plateNumbers.trim().isEmpty() && plateNumbers.length() >= 6) {
            client.setPlateNumbers(plateNumbers);
        }

        // can have letters, numbers, ., _
        client.setUsername(validInput(input, "Username: ", "^[a-zA-Z0-9._]+$", "Invalid input. Please enter a valid username (letters, numbers, ., _).", 5, 15));

        // can have only letters and numbers
        client.setPassword(validInput(input, "Password: ", "^[a-zA-Z0-9]+$", "Invalid input. Please enter a valid password (letters and numbers only).", 8, 20));

        System.out.println("Registration completed successfully!");
    }

    public void mainMenu(Scanner input) {
        int selection = -1;

        System.out.println("\n-------------------------");
        System.out.println("         MAIN MENU         ");
        System.out.println("-------------------------");

//        System.out.println("1 - Reserve a charging station ");
//        System.out.println("2 - Check the reservation status ");
//        System.out.println("3 - Check the charging status ");
//        System.out.println("4 - Find a charging station ");
//        System.out.println("5 - Quit");

        System.out.println("Please enter your immatriculation or reservation number.");
        System.out.println("1 - License number");
        System.out.println("2 - Reservation number");
        System.out.println("3 - Quit");

        while (true) {
            System.out.print("Enter the number of your choice: ");
            if (input.hasNextInt()) {
                selection = input.nextInt();
                input.nextLine();
                if (selection >= 1 && selection <= 3) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
        }

        switch (selection) {
            case 1:
                System.out.print("Please, enter your license number: ");
                String licenseNumber = input.nextLine();
                System.out.println("License number entered: " + licenseNumber);

                break;
            case 2:
                System.out.print("Please, enter your reservation number: ");
                String reservationNumber = input.nextLine();
                System.out.println("Reservation number entered: " + reservationNumber);



                break;
            case 3:
                System.out.println("--- Bye! ---");
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
        }
    }


    public void findReservation(Scanner input){

    }

    public void checkReservationStatus(Scanner input){

    }

    public void findChargingStation(Scanner input){

    }

    public void checkChargingStatus(Scanner input){

    }

    // regular for checking if input is valid
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
