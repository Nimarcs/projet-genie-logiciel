package fr.ul.miage.genielogiciel.parking;

import java.util.Scanner;

public class CommandLine {


    public void run(Scanner scanner, Client client, ClientList clients, ChargingStationList chargingStations) {
        ClientService clientService = new ClientService();
        MenuService menuService = new MenuService();
        ReservationService reservationService = new ReservationService();
        reservationList reservations = new reservationList();


        System.out.println("Welcome to the FastBorne!");
        int userChoice;
        boolean successLogin;

        do {
            userChoice = menuService.welcomeMenu(scanner);

            switch (userChoice) {
                case 1:
                    successLogin = clientService.loginForm(scanner, client);
                    if (successLogin) {
                        menuService.mainMenu(scanner, chargingStations, clients, reservationService, reservations);
                    }
                    break;
                case 2:
                    clientService.registrationForm(scanner, client);
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
}



//package fr.ul.miage.genielogiciel.parking;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Objects;
//import java.util.Scanner;
//
//
//
///**
// * Command line interface for the application.
// * Provides methods to interact with the user via the command line.
// *
// * @since 1.0
// */
//public class CommandLine {
//
//    private static Scanner scanner;
//
//    /**
//     * Start the command line interface for the FastBorne application.
//     * Initialize the scanner and processes user input.
//     * <p>
//     * This method:
//     * <ul>
//     *   <li>Initializes the list of clients and charging stations for testing (will change)</li>
//     *   <li>Display {@link CommandLine#welcomeMenu(Scanner)} and handles user choices</li>
//     *   <li>Allows users to log in {@link CommandLine#loginForm(Scanner, Client)}, register {@link CommandLine#registrationForm(Scanner, Client)}, or quit the application</li>
//     *   <li>Directs logged-in users to the main menu to manage reservations</li>
//     * </ul>
//     */
//
//    public void run(Client client,ClientList clients, ChargingStationList chargingStations) {
//        scanner = new Scanner(System.in);
//        System.out.println("Welcome to the FastBorne!");
//        int userChoice;
//        boolean successLogin;
//
//
//        // handle the user choices
//        do {
//
//            userChoice = welcomeMenu(scanner);
//
//            switch (userChoice) {
//                // login
//                case 1:
//                    successLogin = loginForm(scanner, client);
//                    // Directs logged-in users to the main menu to manage reservations
//                    if (successLogin) {
//                        mainMenu(scanner, chargingStations, clients);
//                    }
//                    break;
//                case 2:
//                    // registration
//                    registrationForm(scanner, client);
//                    break;
//                case 3:
//                    // quit
//                    System.out.println("--- Bye! ---");
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
//            }
//        } while (userChoice != 3);
//        scanner.close();
//    }
//
//
//    /**
//     * Displays the welcome menu and returns the user's choice.
//     *<p> Is using {@link CommandLine#checkInputMenu(Scanner, int)} to get and validate user selection </p>
//     *
//     * @param input the Scanner object to read user input
//     * @return the user's choice
//     */
//    public int welcomeMenu(Scanner input) {
//        int selection = -1;
//
//        System.out.println("\n-------------------------");
//        System.out.println("          WELCOME         ");
//        System.out.println("-------------------------");
//        System.out.println("1 - Login");
//        System.out.println("2 - Create Account");
//        System.out.println("3 - Quit");
//
//        System.out.print("Enter the number of your choice: ");
//
//        selection = checkInputMenu(input, 3);
////        while (true) {
////            System.out.print("Enter the number of your choice: ");
////            if (input.hasNextInt()) {
////                selection = input.nextInt();
////                if (selection >= 1 && selection <= 3) {
////                    break;
////                } else {
////                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
////                }
////            } else {
////                System.out.println("Invalid input. Please enter a number.");
////                input.next();
////            }
////        }
//
//        return selection;
//    }
//
//    /**
//     * Handle user login.
//     * <p>Checks if the credentials have a good format by {@link CommandLine#validInput(Scanner, String, String, String, int, int)}</p>
//     * <p>Allow up to three attempts to enter the correct password, otherwise the application will be closed </p>
//     *
//     * @param input the Scanner object to read user input
//     * @param client the client attempting to log in
//     * @return true if login is successful, false otherwise
//     */
//    public boolean loginForm(Scanner input, Client client) {
//
//        // TODO better logic of login
//
//        System.out.println("\n-------------------------");
//        System.out.println("        LOGIN FORM         ");
//        System.out.println("-------------------------");
//
//        System.out.println("Enter your credentials:");
//
//        // Read and validate the username
//        String username = validInput(input, "Username: ", "^[a-zA-Z0-9._]+$", "Invalid input. Please enter a valid username (letters, numbers, ., _).", 5, 15);
//
//        // Check if the entered username matches the client's username
//        if (Objects.equals(client.getUsername(), username)) {
//            // 3 attempts
//            for (int i = 0; i < 3; i++) {
//                String password = validInput(input, "Password: ", "^[a-zA-Z0-9]+$", "Invalid input. Please enter a valid password (letters and numbers only).", 8, 20);
//                // Check if the entered password matches the client's password
//                if (Objects.equals(client.getPassword(), password)) {
//                    System.out.println("Login successful!");
//                    return true;
//                } else {
//                    System.out.println("Invalid password. Please try again. (" + (2 - i) + " attempts remaining)");
//                }
//            }
//
//            // If the user fails to enter the correct password in three attempts
//            System.out.println("Too many failed attempts. Login failed.");
//        } else {
//            System.out.println("Invalid username. Please try again.");
//        }
//
//        return false;
//    }
//
//
//    /**
//     * Handle user registration.
//     * <p>This method prompts the user to enter their credentials</p>
//     * <p>Is using {@link CommandLine#validInput(Scanner, String, String, String, int, int)} for checking the valid input</p>
//     * @param input the Scanner object to read user input
//     * @param client the client being registered
//     */
//    public void registrationForm(Scanner input, Client client) {
//        System.out.println("\n-------------------------");
//        System.out.println("     REGISTRATION FORM     ");
//        System.out.println("-------------------------");
//
//        System.out.println("Enter your credentials");
//
//        // Prompt and validate the credentials
//
//        // Name -  can have only letters
//        client.setName(validInput(input, "Name: ", "^[a-zA-Z\\s]+$", "Invalid input. Please enter a valid name without numbers or special characters.", 2, 20));
//        client.setSurname(validInput(input, "Surname: ", "^[a-zA-Z\\s]+$", "Invalid input. Please enter a valid surname without numbers or special characters.", 2, 20));
//
//        // Surname - can have letters and numbers
//        client.setAdresse(validInput(input, "Address: ", "^[a-zA-Z0-9\\s,]+$", "Invalid input. Please enter a valid address without special characters.", 5, 40));
//
//        // Phone number - can have numbers and symbol +
//        client.setPhoneNumber(validInput(input, "Phone Number: ", "^[+]?\\d+$", "Invalid input. Please enter a valid phone number. It may contain only numbers and an optional leading +.", 10, 12));
//
//        // Email - can have numbers, letters and symbols @, ., -
//        client.setEmail(validInput(input, "Email: ", "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", "Invalid input. Please enter a valid email address.", 5, 50));
//
//        // Credit card number
//        String creditCard = validInput(input, "Credit Card Number: ", "^[0-9]+$", "Invalid input. Please enter a valid credit card number.", 10, 16);
//        client.setCreditCard(creditCard);
//
//        // License plate numbers - can have letters and numbers (optional)
//        String plateNumbers = validInput(input, "License Plate Numbers (optional - press enter to skip): ", "^[a-zA-Z0-9]+$", "Invalid input. Please enter a valid license plate number.", 0, 12);
//        if (!plateNumbers.trim().isEmpty() && plateNumbers.length() >= 6) {
//            client.setPlateNumbers(plateNumbers);
//        }
//
//        // Username - can have letters, numbers, ., _
//        client.setUsername(validInput(input, "Username: ", "^[a-zA-Z0-9]+$", "Invalid input. Please enter a valid username (letters, numbers, ., _).", 5, 15));
//
//        // Password - can have only letters and numbers
//        client.setPassword(validInput(input, "Password: ", "^[a-zA-Z0-9]+$", "Invalid input. Please enter a valid password (letters and numbers only).", 8, 20));
//
//        System.out.println("Registration completed successfully!");
//    }
//
//    /**
//     * Display the main menu and processes the user's choice.
//     * <p> Is using {@link CommandLine#checkInputMenu(Scanner, int)} to get and validate user selection </p>
//     * <p>
//     * This method allows the user to enter:
//     * <ul>
//     *     <li>immatriculation number (license) </li>
//     *     <li>reservation number</li>
//     *     <li>indicate that they don't have a reservation (redirect to {@link CommandLine#reserveChargingStation(Scanner, ChargingStationList, ClientList)})</li>
//     *     <li>go back to the previous menu</li>
//     * </ul>
//     *</p>
//     * @param input            the Scanner object to read user input
//     * @param chargingStations the list of charging stations
//     * @param clients          the list of clients
//     */
//    public void mainMenu(Scanner input, ChargingStationList chargingStations, ClientList clients) {
//
//
//        int selection = -1;
//
//        System.out.println("\n-------------------------");
//        System.out.println("         MAIN MENU         ");
//        System.out.println("-------------------------");
//
//
//        System.out.println("\nPlease enter your immatriculation or reservation number.");
//        System.out.println("1 - License number");
//        System.out.println("2 - Reservation number");
//        System.out.println("3 - I don't have the reservation");
//        System.out.println("4 - Back");
//
//        // Get and validate user selection
//        selection =  checkInputMenu(input, 4);
//
//        switch (selection) {
//            // Handle license number input
//            case 1:
//                System.out.print("Please, enter your license number: ");
//                String licenseNumber = input.nextLine();
//                System.out.println("License number entered: " + licenseNumber);
//
//                break;
//
//            // Handle reservation number input
//            case 2:
//                System.out.print("Please, enter your reservation number: ");
//                String reservationNumber = input.nextLine();
//                System.out.println("Reservation number entered: " + reservationNumber);
//
//
//
//                break;
//
//            // Handle case when user does not have a reservation
//            case 3:
//                System.out.print("Waiting...");
//                reserveChargingStation(input, chargingStations, clients);
//                break;
//            // go back
//            case 4:
//                System.out.println("Returning back...  ---");
//                break;
//            default:
//                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
//        }
//
//    }
//
//
//    /**
//     * Reserves a charging station for a client without a prior reservation
//     * <p>
//     * This method checks for available charging stations and allows a registered client to make a reservation
//     * by entering their license number or mobile number. If the client is not recognized, they are prompted to create a new account.
//     * </p>
//     *
//     * <p> Is using {@link CommandLine#checkInputMenu(Scanner, int)} to get and validate user selection </p>
//     *
//     * @param input            the Scanner object to read user input
//     * @param chargingStations the list of charging stations
//     * @param clients          the list of clients
//     */
//
//    private void reserveChargingStation(Scanner input, ChargingStationList chargingStations, ClientList clients) {
//        LocalDateTime currentTime = LocalDateTime.now();
//        LocalDateTime endTime ;
//        List<ChargingStation> availableStations = chargingStations.findAvailableStations();
//
//
//        // Check if there are available charging stations
//        if (!availableStations.isEmpty()) {
//            System.out.println("Available station found: ");
//
//            int i = 1;
//            int selection = -1;
//
//            // Display available charging stations
//            for (ChargingStation station : availableStations) {
//                System.out.println(i + ". ID: " + station.getIdStation());
//                i++;
//            }
//
//            System.out.print("Please enter the ID of the station you want to reserve: ");
//            selection = input.nextInt();
//
//
//            ChargingStation selectedStation = chargingStations.findChargingStation(selection);
//
//            // Check if the selected station is available
//            if (selectedStation != null && selectedStation.getDisponible()) {
//                System.out.print("Please enter your license number: ");
//                String licenseNumber = input.nextLine();
//
//                Client client = clients.findClientByLicense(licenseNumber);
//
//                // If the vehicle's license number is recognized
//                if (client != null) {
//                    selection = -1;
//                    System.out.println("Choose what to enter: ");
//                    System.out.println("1. Expected duration ");
//                    System.out.println("2. Departure time ");
//
//                    selection = checkInputMenu(input, 3);
//
//
//                    // Allow the client to specify the expected charging duration or departure time
//                    switch (selection) {
//                        case 1:
//                            System.out.println("Enter expected duration: ");
//                            int duration = input.nextInt();
//                            endTime = currentTime.plusHours(duration);
//
//                            break;
//                        case 2:
//                            System.out.println("Enter departure time: ");
//                            int hour = input.nextInt();
//                            endTime = currentTime.withHour(hour);
//
//                            break;
//                        default:
//                            endTime = currentTime.plusHours(1);
//                    }
//
//                    // Add the reservation to the selected charging station
//                    selectedStation.addReservation(new Reservation(client, LocalDateTime.now(), endTime));
//                } else {
//                    // If the vehicle's license number is not recognizeв, it proposed to enter the mobile phone
//                    System.out.print("License number not recognized. Please enter your mobile number: ");
//                    String mobileNumber = input.nextLine();
//
//                    Client client2 = clients.findByMobilePhone(mobileNumber);
//
//
//                    if (client2 != null ){
//                        System.out.print("Please enter the expected charging duration (in hours): ");
//                        int duration = input.nextInt();
//
//                        System.out.println("Temporary reservation made for mobile number " + mobileNumber + " with duration " + duration + " hours.");
//                        selectedStation.addReservation(new Reservation(client, currentTime, currentTime.plusHours(duration)));
//                    } else {
//
//                        // If the mobile number is not recognized, prompt to create a new account
//                        System.out.println("We didn't find the account associated with thus number. Please create new one: ");
//                        welcomeMenu(input);
//                    }
//
//
//                }
//            } else {
//                System.out.println("Selected station is not available.");
//            }
//        } else {
//            System.out.println("No available stations at the moment. Please try again later.");
//        }
//    }
//
//
//
//    public void findReservation(Scanner input){
//        // TODO finding reservation
//    }
//
//    public void checkReservationStatus(Scanner input){
//        // TODO checking reservation status
//    }
//
//
//    /**
//     * Validates and returns a user's menu selection.
//     * <p>
//     * This method prompts the user to enter a number corresponding to a menu option and ensures that the input is a valid integer within the specified range.
//     * If the input is invalid, the user is prompted to enter a valid number until a valid selection is made.
//     * </p>
//     *
//     * @param input          the Scanner object to read user input
//     * @param maxPointsMenu  the maximum number of menu options
//     * @return the validated menu selection
//     */
//    private int checkInputMenu(Scanner input, int maxPointsMenu){
//        int selection = -1;
//
//        while (true) {
//            System.out.print("Enter the number of your choice: ");
//            if (input.hasNextInt()) {
//                selection = input.nextInt();
//                input.nextLine();
//                if (selection >= 1 && selection <= maxPointsMenu) {
//                    break;
//                } else {
//                    System.out.println("Invalid input. Please enter a number between 1 and " + maxPointsMenu);
//                }
//            } else {
//                System.out.println("Invalid input. Please enter a number.");
//                input.next();
//            }
//        }
//
//        return selection;
//    }
//
//    /**
//     * Prompts the user for input and validates it against specified criteria.
//     * <p> The algorithm:
//     * <ul>
//     * <li>Prompt the user to enter input</li>
//     * <li>Check if the input matches the specified criteria (length and regex)</li>
//     * <li>If valid, return the input</li>
//     * <li>If invalid, display an error message and increment the attempt counter</li>
//     * <li>If the maximum number of attempts is reached, throw an exception</li>
//     * </ul>
//     * </p>
//     *
//     * @param input        the Scanner object to read user input
//     * @param prompt       the message to prompt the user
//     * @param regex        the regular expression to validate the input
//     * @param errorMessage the error message to display for invalid input
//     * @param minLength    the minimum length of the valid input
//     * @param maxLength    the maximum length of the valid input
//     * @return the validated user input
//     * @throws IllegalArgumentException if the user exceeds the maximum number of attempts
//     */
// private String validInput(Scanner input, String prompt, String regex, String errorMessage, int minLength, int maxLength) {
//        String userInput;
//        int attempts = 0;
//        int maxAttempts = 3;
//
//        while (attempts < maxAttempts) {
//            System.out.print(prompt);
//            userInput = input.nextLine().trim();
//            if (userInput.length() >= minLength && userInput.length() <= maxLength && userInput.matches(regex)) {
//                return userInput;
//            } else {
//                System.out.println(errorMessage);
//                attempts++;
//            }
//        }
//
//        throw new IllegalArgumentException("Too many invalid attempts.");
//    }
//}
