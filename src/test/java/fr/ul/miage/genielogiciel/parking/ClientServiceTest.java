package fr.ul.miage.genielogiciel.parking;

import fr.ul.miage.genielogiciel.parking.commandLine.ClientDisplayService;
import fr.ul.miage.genielogiciel.parking.commandLine.DisplayerSout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    private ClientDisplayService clientService;
    private ArrayList<Client> clients;
    private Scanner input;
    private FacadeInterface facadeInterface;

    @BeforeEach
    public void setUp() {
        clients = new ArrayList<>();
        input = mock(Scanner.class);
        clientService = new ClientDisplayService(input, new DisplayerSout());
        facadeInterface = new FacadeInterface();
        facadeInterface.initializeParking(new ArrayList<>(), clients, new ArrayList<>());

        // Add a test client
        Client client = new Client();
        client.setUsername("testUser");
        client.setPassword("testPass");
        clients.add(client);
    }

    // Login success
    @Test
    public void testLoginFormSuccess() {
        when(input.nextLine())
                .thenReturn("testUser") // username input
                .thenReturn("testPass"); // password input


        clientService.loginForm(facadeInterface);
        Client result = facadeInterface.getCurrentClient();
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    // login invalid username
    @Test
    public void testLoginFormInvalidUsername() {
        when(input.nextLine())
                .thenReturn("wrongUser") // username input
                .thenReturn("wrongUser")
                .thenReturn("wrongUser");

        clientService.loginForm(facadeInterface);
        Client result = facadeInterface.getCurrentClient();
        assertNull(result);
    }

    // login invalid password
    @Test
    public void testLoginFormInvalidPassword() {
        when(input.nextLine())
                .thenReturn("testUser") // username input
                .thenReturn("wrongPass") // password input
                .thenReturn("wrongPass")
                .thenReturn("wrongPass");

        clientService.loginForm(facadeInterface);
        Client result = facadeInterface.getCurrentClient();
        assertNull(result);
    }

    // registration success

    @Test
    public void testRegistrationFormSuccess() {
        when(input.nextLine())
                .thenReturn("John")    // Name
                .thenReturn("Doe")     // Surname
                .thenReturn("123 Street") // Address
                .thenReturn("0786656273") // Phone Number
                .thenReturn("john@example.com") // Email
                .thenReturn("1111222233334444") // Credit Card Number
                .thenReturn("newUser")  // Username
                .thenReturn("newPass123") // Password
                .thenReturn("ABC123");  // License Plate Numbers

        clientService.registrationForm(facadeInterface);
        assertEquals(2, clients.size());
        assertEquals("newUser", clients.get(1).getUsername());
    }
}
