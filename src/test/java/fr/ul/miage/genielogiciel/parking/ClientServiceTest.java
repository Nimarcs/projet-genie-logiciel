package fr.ul.miage.genielogiciel.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    private ClientService clientService;
    private ArrayList<Client> clients;
    private Scanner input;
    private Client client;

    @BeforeEach
    void setUp() {
        clientService = new ClientService();
        clients = new ArrayList<>();
        input = mock(Scanner.class);
        client = new Client();
        client.setUsername("testUser");
        client.setPassword("testPassword");
        clients.add(client);
    }

    @Test
    void testLoginFormSuccessful() {
        when(input.nextLine()).thenReturn("testUser", "testPassword");

        boolean result = clientService.loginForm(input, clients);

        assertTrue(result);
        verify(input, times(2)).nextLine(); // Verifies that nextLine() is called twice
    }

    @Test
    void testLoginFormInvalidUsername() {
        when(input.nextLine()).thenReturn("wrongUser", "wrongUser", "wrongUser");

        boolean result = clientService.loginForm(input, clients);

        assertFalse(result);
        verify(input, times(3)).nextLine();
    }

    @Test
    void testLoginFormInvalidPassword() {
        when(input.nextLine()).thenReturn("testUser", "wrongPassword", "wrongPassword", "wrongPassword");

        boolean result = clientService.loginForm(input, clients);

        assertFalse(result);
        verify(input, times(4)).nextLine();
    }

    @Test
    void testRegistrationFormSuccessful() {
        when(input.nextLine())
                .thenReturn("John")
                .thenReturn("Doe")
                .thenReturn("123 street")
                .thenReturn("0786656273")
                .thenReturn("john.doe@example.com")
                .thenReturn("1111222233334444")
                .thenReturn("ABC123")
                .thenReturn("newUser")
                .thenReturn("newPassword");

        clientService.registrationForm(input, client);

        assertEquals("John", client.getName());
        assertEquals("Doe", client.getSurname());
        assertEquals("123 street", client.getAdresse());
        assertEquals("0786656273", client.getPhoneNumber());
        assertEquals("john.doe@example.com", client.getEmail());
        assertEquals("1111222233334444", client.getCreditCard());
        assertEquals("ABC123", client.getPlateNumbers());
        assertEquals("newUser", client.getUsername());
        assertEquals("newPassword", client.getPassword());
    }

    @Test
    void testRegistrationFormInvalidName() {
        when(input.nextLine())
                .thenReturn("", "John") // Invalid then valid input
                .thenReturn("Doe")
                .thenReturn("123 street")
                .thenReturn("0786656273")
                .thenReturn("john.doe@example.com")
                .thenReturn("1111222233334444")
                .thenReturn("ABC123")
                .thenReturn("newUser")
                .thenReturn("newPassword");

        clientService.registrationForm(input, client);

        assertEquals("John", client.getName());
        assertEquals("Doe", client.getSurname());
        assertEquals("123 street", client.getAdresse());
        assertEquals("0786656273", client.getPhoneNumber());
        assertEquals("john.doe@example.com", client.getEmail());
        assertEquals("1111222233334444", client.getCreditCard());
        assertEquals("ABC123", client.getPlateNumbers());
        assertEquals("newUser", client.getUsername());
        assertEquals("newPassword", client.getPassword());
    }

    @Test
    void testRegistrationFormTooManyInvalidAttempts() {
        when(input.nextLine())
                .thenReturn("", "", "", "") // Invalid inputs
                .thenReturn("Doe")
                .thenReturn("123 street")
                .thenReturn("0786656273")
                .thenReturn("john.doe@example.com")
                .thenReturn("1111222233334444")
                .thenReturn("ABC123")
                .thenReturn("newUser")
                .thenReturn("newPassword");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clientService.registrationForm(input, client);
        });

        assertEquals("Too many invalid attempts.", exception.getMessage());
    }
}
