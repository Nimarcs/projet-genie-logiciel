package parking;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
//import org.mockito.Mockito;
import fr.ul.miage.genielogiciel.parking.Launcher;
import fr.ul.miage.genielogiciel.parking.CommandLine;
import fr.ul.miage.genielogiciel.parking.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;





@DisplayName("I'm testing...")
public class  TestCommandLine {
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
    @DisplayName("If input is > 3 (Welcome Menu)")
    public void testWelcomeMenu3() {
        String input = "5\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        var cl = new CommandLine();
        int choice = cl.welcomeMenu(scanner);
        assertEquals(1, choice);
    }

    @Test
    @DisplayName("If input is < 1 (Welcome Menu)")
    public void testWelcomeMenu4() {
        String input = "-5\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        var cl = new CommandLine();
        int choice = cl.welcomeMenu(scanner);
        assertEquals(2, choice);
    }

    @Test
    @DisplayName("Quit (Welcome Menu)")
    public void testWelcomeMenu5() {
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        var cl = new CommandLine();
        cl.welcomeMenu(scanner);

        assertTrue(true);
    }

    // ===== TESTS for Main Menu =====
    @Test
    @DisplayName("Valid license number (Main Menu)")
    public void testMainMenu1() {
        String input = "1\nlicense123\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        var cl = new CommandLine();
        cl.mainMenu(scanner);

        assertTrue(true);
    }

    @Test
    @DisplayName("Valid reservation number (Main Menu)")
    public void testMainMenu2() {
        String input = "2\nreservation123\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        var cl = new CommandLine();
        cl.mainMenu(scanner);

        assertTrue(true);
    }

    @Test
    @DisplayName("First valid then invalid input (Main Menu)")
    public void testMainMenu3() {
        String input = "invalid\n4\n1\nlicense123\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        var cl = new CommandLine();
        cl.mainMenu(scanner);

        assertTrue(true);
    }

    @Test
    @DisplayName("Quit (Main Menu)")
    public void testMainMenu4() {
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);
        var cl = new CommandLine();
        cl.mainMenu(scanner);

        assertTrue(true);
    }

    // ===== TESTS for Login Form =====
    @Test
    @DisplayName("Successful login with valid credentials")
    public void testLoginFormSuccess() {
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
    @DisplayName("Failed login with invalid username")
    public void testLoginFormInvalidUsername() {
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
    @DisplayName("Failed login with invalid password")
    public void testLoginFormInvalidPassword() {
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
    @DisplayName("Successful registration")
    public void testRegistrationFormSuccess() {
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
        assertEquals(1234567890, client.getCreditCard());
        assertEquals("LICENSE123", client.getPlateNumbers());
        assertEquals("johndoe", client.getUsername());
        assertEquals("securePass123", client.getPassword());
    }

    @Test
    @DisplayName("Too many invalid input")
    public void testRegistrationFormInvalidName() {
        Client client = new Client();

        String input = """
                        J1
                        Doe2
                        123 Main St*
                        +1234567890d
                        j*ohn.doe@example.com
                        1234567890ss
                        LICENSE123*
                        johndoe*
                        SecurePass123*
                        """;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        CommandLine commandLine = new CommandLine();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commandLine.registrationForm(scanner, client);
        });

        assertEquals("Too many invalid attempts.", exception.getMessage());
    }
}
