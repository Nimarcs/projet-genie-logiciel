package fr.ul.miage.genielogiciel.parking;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
//import org.mockito.Mockito;


import fr.ul.miage.genielogiciel.parking.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import fr.ul.miage.genielogiciel.parking.Client;

import fr.ul.miage.genielogiciel.parking.CommandLine;
import fr.ul.miage.genielogiciel.parking.ListChargingStation;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;



@DisplayName("I'm testing...")
public class TestCommandLine {
//    private InputStream originalIn;

    // ===== TESTS for Welcome Menu =====
    @Test
    @DisplayName("1. Correct input (Welcome Menu)")
    public void testWelcomeMenu1() {
        String input = "1\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        var cl = new CommandLine();
        int choice = cl.welcomeMenu(scanner);

        assertEquals(1, choice);
    }

    @Test
    @DisplayName("2. 'abc' input (Welcome Menu)")
    public void testWelcomeMenu2() {
        String input = "abc\n4\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        var cl = new CommandLine();
        int choice = cl.welcomeMenu(scanner);
        assertEquals(2, choice);
    }

    @Test
    @DisplayName("3. If input is > 3 (Welcome Menu)")
    public void testWelcomeMenu3() {
        String input = "5\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        var cl = new CommandLine();
        int choice = cl.welcomeMenu(scanner);
        assertEquals(1, choice);
    }

    @Test
    @DisplayName("4. If input is < 1 (Welcome Menu)")
    public void testWelcomeMenu4() {
        String input = "-5\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        var cl = new CommandLine();
        int choice = cl.welcomeMenu(scanner);
        assertEquals(2, choice);
    }

    @Test
    @DisplayName("5. Quit (Welcome Menu)")
    public void testWelcomeMenu5() {
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        var cl = new CommandLine();
        cl.welcomeMenu(scanner);

        assertTrue(true);
    }


    // ===== TESTS for Login Form =====
    @Test
    @DisplayName("[LOGIN FORM] Successful login with valid credentials")
    public void testLoginForm1() {
        Client client = new Client();
        client.setUsername("validUser");
        client.setPassword("validPassword123");

        String input = "validUser\nvalidPassword123\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        boolean result = commandLine.loginForm(scanner, client);

        assertTrue(result, "Login is ok");
    }

    @Test
    @DisplayName("[LOGIN FORM] Failed login with invalid username")
    public void testLoginForm2() {
        Client client = new Client();
        client.setUsername("validUser");
        client.setPassword("validPassword123");

        String input = "invalidUser\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        boolean result = commandLine.loginForm(scanner, client);

        assertFalse(result, "Login failed. Bad username");
    }

    @Test
    @DisplayName("[LOGIN FORM] Failed login with invalid password")
    public void testLoginForm3() {
        Client client = new Client();
        client.setUsername("validUser");
        client.setPassword("validPassword123");

        String input = "validUser\ninvalidPassword\ninvalidPassword\ninvalidPassword\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        boolean result = commandLine.loginForm(scanner, client);

        assertFalse(result, "Login failed. Bad password");
    }


    // ===== TESTS for Registration Form =====
    @Test
    @DisplayName("[REGISTRATION FORM] Successful registration ")
    public void testRegistrationForm1() {
        Client client = new Client();

        String input = """ 
                John
                Doe
                123 Main St
                +1234567890
                john.doe@example.com
                1234567890
                LICENSE123
                johndoe
                securePass123
                """;

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        commandLine.registrationForm(scanner, client);

        assertEquals("John", client.getName());
        assertEquals("Doe", client.getSurname());
        assertEquals("123 Main St", client.getAdresse());
        assertEquals("+1234567890", client.getPhoneNumber());
        assertEquals("john.doe@example.com", client.getEmail());
        assertEquals("1234567890", client.getCreditCard());
        assertEquals("LICENSE123", client.getPlateNumbers());
        assertEquals("johndoe", client.getUsername());
        assertEquals("securePass123", client.getPassword());
    }

    // from testRegistrationForm2 to testRegistrationForm10
    // -- testing if the inputs have the correct format
    @Test
    @DisplayName("[REGISTRATION FORM] Invalid name + Too many invalid input")
    public void testRegistrationForm2() {
        Client client = new Client();

        String input = """
                        J1
                        Doe
                        123 Main St
                        +1234567890
                        john.doe@example.com
                        1234567890ss
                        LICENSE123
                        johndoe
                        SecurePass123
                        """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commandLine.registrationForm(scanner, client);
        });

        assertEquals("Too many invalid attempts.", exception.getMessage());
    }

    @Test
    @DisplayName("[REGISTRATION FORM] 3. Invalid surname")
    public void testRegistrationForm3() {
        Client client = new Client();

        String input = """
                        Joe
                        Doe*
                        123 Main St
                        +1234567890
                        john.doe@example.com
                        1234567890ss
                        LICENSE123
                        johndoe
                        SecurePass123
                        """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> commandLine.registrationForm(scanner, client));

        assertEquals("Too many invalid attempts.", exception.getMessage());
    }

    @Test
    @DisplayName("[REGISTRATION FORM] 4. Invalid adresse")
    public void testRegistrationForm4() {
        Client client = new Client();

        String input = """
                        Joe
                        Doe
                        123 Main St!!!
                        +1234567890
                        john.doe@example.com
                        1234567890
                        LICENSE123
                        johndoe
                        SecurePass123
                        """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commandLine.registrationForm(scanner, client);
        });

        assertEquals("Too many invalid attempts.", exception.getMessage());
    }

    @Test
    @DisplayName("[REGISTRATION FORM] 5. Invalid number")
    public void testRegistrationForm5() {
        Client client = new Client();

        String input = """
                        Joe
                        Doe
                        123 Main St
                        abc1234
                        john.doe@example.com
                        1234567890
                        LICENSE123
                        johndoe
                        SecurePass123
                        """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commandLine.registrationForm(scanner, client);
        });

        assertEquals("Too many invalid attempts.", exception.getMessage());
    }

    @Test
    @DisplayName("[REGISTRATION FORM] 6. Invalid email")
    public void testRegistrationForm6() {
        Client client = new Client();

        String input = """
                        Joe
                        Doe
                        123 Main St
                        +1234567890
                        john.doe
                        1234567890
                        LICENSE123
                        johndoe
                        SecurePass123
                        """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commandLine.registrationForm(scanner, client);
        });

        assertEquals("Too many invalid attempts.", exception.getMessage());
    }


    @Test
    @DisplayName("[REGISTRATION FORM] 7. Invalid card number")
    public void testRegistrationForm7() {
        Client client = new Client();

        String input = """
                        Joe
                        Doe
                        123 Main St
                        +1234567890
                        john.doe@gmail.com
                        1234567890ss
                        LICENSE123
                        johndoe
                        SecurePass123
                        """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commandLine.registrationForm(scanner, client);
        });

        assertEquals("Too many invalid attempts.", exception.getMessage());
    }

    @Test
    @DisplayName("[REGISTRATION FORM] 8. Invalid card license number")
    public void testRegistrationForm8() {
        Client client = new Client();

        String input = """
                        Joe
                        Doe
                        123 Main St
                        +1234567890
                        john.doe@gmail.com
                        1234567890
                        !LIC1234!
                        johndoe
                        SecurePass123
                        """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commandLine.registrationForm(scanner, client);
        });

        assertEquals("Too many invalid attempts.", exception.getMessage());
    }

    @Test
    @DisplayName("[REGISTRATION FORM] 9. Invalid username")
    public void testRegistrationForm9() {
        Client client = new Client();

        String input = """
                        Joe
                        Doe
                        123 Main St
                        +1234567890
                        john.doe@gmail.com
                        1234567890
                        LIC1234
                        john*doe
                        SecurePass123
                        """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commandLine.registrationForm(scanner, client);
        });

        assertEquals("Too many invalid attempts.", exception.getMessage());
    }

    @Test
    @DisplayName("[REGISTRATION FORM] 10. Invalid password")
    public void testRegistrationForm10() {
        Client client = new Client();

        String input = """
                        Joe
                        Doe
                        123 Main St
                        +1234567890
                        john.doe@gmail.com
                        1234567890
                        LIC1234
                        johndoe
                        Secure*Pass123
                        """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commandLine.registrationForm(scanner, client);
        });

        assertEquals("Too many invalid attempts.", exception.getMessage());
    }



    // ===== TESTS for Main Menu =====
    @Test
    @DisplayName("[MAIN MENU] 1. Valid license number")
    public void testMainMenu1() {
        String input = "1\nlicense123\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientList clients = new ClientList();
        ListChargingStation chargingStations = new ListChargingStation();

        var cl = new CommandLine();
        cl.mainMenu(scanner, chargingStations, clients);

        assertTrue(true);
    }

    @Test
    @DisplayName("[MAIN MENU] 2. Valid reservation number")
    public void testMainMenu2() {
        String input = "2\nreservation123\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientList clients = new ClientList();
        ListChargingStation chargingStations = new ListChargingStation();

        var cl = new CommandLine();
        cl.mainMenu(scanner, chargingStations, clients);

        assertTrue(true);
    }

    @Test
    @DisplayName("[MAIN MENU] 3. First valid then invalid input")
    public void testMainMenu3() {
        String input = "invalid\n4\n1\nlicense123\n4\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientList clients = new ClientList();
        ListChargingStation chargingStations = new ListChargingStation();

        var cl = new CommandLine();
        cl.mainMenu(scanner, chargingStations, clients);

        assertTrue(true);
    }

    @Test
    @DisplayName("[MAIN MENU] 4. I don't have a reservation + no available station")
    public void testMainMenu4() {
        String input = "3\n123\nlicense123\n1\n2\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientList clients = new ClientList();
        ListChargingStation chargingStations = new ListChargingStation();

        var cl = new CommandLine();
        cl.mainMenu(scanner, chargingStations, clients);

        assertTrue(true);
    }

    @Test
    @DisplayName("[MAIN MENU] 5. I don't have a reservation + no license + no mobile phone")
    public void testMainMenu5() {

        String input = "3\n123\nlicense123\n1\n2\n3\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientList clients = new ClientList();
        clients.addClient(new Client("Lara", "Mara", "39 rue Paris, 54000 Nancy", "+33785546942", "test@gmail.com", "00012342", "LICENSE123", "test", "test"));


        ListChargingStation chargingStations = new ListChargingStation();
        chargingStations.addStation(new ChargingStation(123, true));

        var cl = new CommandLine();
        cl.mainMenu(scanner, chargingStations, clients);

        assertTrue(true);
    }

    @Test
    @DisplayName("[MAIN MENU] 6. I don't have a reservation + I have license")
    public void testMainMenu6() {

        String input = "3\n123\nlicense123\n1\n2\n3\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientList clients = new ClientList();
        clients.addClient(new Client("Lara", "Mara", "39 rue Paris, 54000 Nancy", "+33785546942", "test@gmail.com", "00012342", "LICENSE123", "test", "test"));

        ListChargingStation chargingStations = new ListChargingStation();
        chargingStations.addStation(new ChargingStation(123, true));


        var cl = new CommandLine();
        cl.mainMenu(scanner, chargingStations, clients);

        assertTrue(true);
    }

    @Test
    @DisplayName("[SEARCH CLIENT] Find client by phone number")
    public void testFindClientByPhoneNumber() {
        ClientList clients = new ClientList();
        clients.addClient(new Client("Lara", "Mara", "39 rue Paris, 54000 Nancy", "+33785546942", "test@gmail.com", "00012342", "LICENSE123", "test", "test"));

        Client foundClient = clients.findByMobilePhone("+33785546942");
        assertNotNull(foundClient);
        assertEquals("Lara", foundClient.getName());
    }


    @Test
    @DisplayName("[MAIN MENU] 7. I don't have a reservation + no license, but I have number phone")
    public void testMainMenu7() {

        String input = "3\n123\ndddd\n+33785546942\n1\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        ClientList clients = new ClientList();
        clients.addClient(new Client("Lara", "Mara", "39 rue Paris, 54000 Nancy", "+33785546942", "test@gmail.com", "00012342", "LICENSE123", "test", "test"));

        ListChargingStation chargingStations = new ListChargingStation();
        chargingStations.addStation(new ChargingStation(123, true));


        var cl = new CommandLine();
        cl.mainMenu(scanner, chargingStations, clients);

        assertTrue(true);
    }

}
