package fr.ul.miage.genielogiciel.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    private ClientService clientService;
    private Client client;
    private ClientList clients;

    @Mock
    private Scanner scannerMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientService();
        client = new Client("john", "doe", "123 Main St", "+12345678901", "john.doe@example.com",
                "1234567812345678", "ABC123", "johndoe", "securePass123");

        clients = new ClientList();
        clients.addClient(client);
    }

    // Tests for loginForm

    @Test
    public void testLoginFormSuccess() {
        when(scannerMock.nextLine()).thenReturn("johndoe", "securePass123");

        assertTrue(clientService.loginForm(scannerMock, clients));
        verify(scannerMock, times(2)).nextLine(); // One for username, one for password
    }

//    @Test
//    public void testLoginFormInvalidUsername() {
//        when(scannerMock.nextLine()).thenReturn("wrongusername");
//
//        assertFalse(clientService.loginForm(scannerMock, clients));
//        verify(scannerMock, times(1)).nextLine(); // One for username
//    }

    @Test
    public void testLoginFormInvalidPassword() {
        when(scannerMock.nextLine()).thenReturn("johndoe", "wrongpassword", "wrongpassword", "wrongpassword");

        assertFalse(clientService.loginForm(scannerMock, clients));
        verify(scannerMock, times(4)).nextLine(); // One for username, three for password attempts
    }

    // Tests for registrationForm

    @Test
    public void testRegistrationFormSuccess() {
        when(scannerMock.nextLine()).thenReturn("Jane", "Doe", "456 elm st", "+12345678901", "jane.doe@example.com",
                "8765432187654321", "DEF456", "janedoe", "securePass456");

        Client newClient = new Client();
        clientService.registrationForm(scannerMock, newClient);

        assertEquals("Jane", newClient.getName());
        assertEquals("Doe", newClient.getSurname());
        assertEquals("456 elm st", newClient.getAdresse());
        assertEquals("+12345678901", newClient.getPhoneNumber());
        assertEquals("jane.doe@example.com", newClient.getEmail());
        assertEquals("8765432187654321", newClient.getCreditCard());
        assertEquals("DEF456", newClient.getPlateNumbers());
        assertEquals("janedoe", newClient.getUsername());
        assertEquals("securePass456", newClient.getPassword());
    }

    @Test
    public void testRegistrationFormInvalidName() {
        when(scannerMock.nextLine()).thenReturn("J", "Doe", "456 Elm St", "+12345678901", "jane.doe@example.com",
                "8765432187654321", "DEF456", "jane_doe", "securePass456");

        Client newClient = new Client();
        assertThrows(IllegalArgumentException.class, () -> clientService.registrationForm(scannerMock, newClient));
    }


}
