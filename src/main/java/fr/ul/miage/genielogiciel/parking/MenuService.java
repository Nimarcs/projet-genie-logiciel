package fr.ul.miage.genielogiciel.parking;

import java.util.Scanner;

public class MenuService {

    public int welcomeMenu(Scanner input) {
        int selection = -1;

        System.out.println("\n-------------------------");
        System.out.println("          WELCOME         ");
        System.out.println("-------------------------");
        System.out.println("1 - Login");
        System.out.println("2 - Create Account");
        System.out.println("3 - Quit");

        selection = checkInputMenu(input, 3);
        return selection;
    }

    public void mainMenu(Scanner input, ChargingStationList chargingStations, ClientList clients, ReservationService reservationService) {
        int selection = -1;

        System.out.println("\n-------------------------");
        System.out.println("         MAIN MENU         ");
        System.out.println("-------------------------");

        System.out.println("\nPlease enter your immatriculation or reservation number.");
        System.out.println("1 - License number");
        System.out.println("2 - Reservation number");
        System.out.println("3 - I don't have the reservation");
        System.out.println("4 - Back");

        selection = checkInputMenu(input, 4);

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
                System.out.print("Waiting...");
                reservationService.reserveChargingStation(input, chargingStations, clients);
                break;
            case 4:
                System.out.println("Returning back...  ---");
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
        }
    }

    private int checkInputMenu(Scanner input, int maxPointsMenu) {
        int selection = -1;

        while (true) {
            System.out.print("Enter the number of your choice: ");
            if (input.hasNextInt()) {
                selection = input.nextInt();
                input.nextLine();
                if (selection >= 1 && selection <= maxPointsMenu) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and " + maxPointsMenu);
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
        }

        return selection;
    }
}

