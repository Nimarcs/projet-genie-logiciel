package fr.ul.miage.genielogiciel.parking;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ClientTest {

    //====== TESTs FOR NAME ======/

    // Test if name is valid
    @Test
    public void testSetNameValid() {
        Client client = new Client();
        client.setName("John");
        assertEquals("John", client.getName());
    }

    // If name is empty -> error
    @Test
    public void testSetNameEmpty() {
        Client client = new Client();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setName(""));
        assertEquals("Invalid name format. Only letters are allowed.", exception.getMessage());
    }

    // If name has invalid format (example: 1234Vasyl*!)
    @Test
    public void testSetNameInvalidFormat() {
        Client client = new Client();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setName("J0hn"));
        assertEquals("Invalid name format. Only letters are allowed.", exception.getMessage());
    }

    //====== TESTs FOR SURNAME ======/

    // Test if surname is valid
    @Test
    public void testSetSurnameValid() {
        Client client = new Client();
        client.setSurname("Doe");
        assertEquals("Doe", client.getSurname());
    }

    // If surname is empty -> error
    @Test
    public void testSetSurnameInvalidEmpty() {
        Client client = new Client();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setSurname(""));
        assertEquals("Invalid surname format. Only letters are allowed.", exception.getMessage());
    }

    // If surname has invalid format (example: 1234Doe*!)
    @Test
    public void testSetSurnameInvalidFormat() {
        Client client = new Client();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setSurname("D03"));
        assertEquals("Invalid surname format. Only letters are allowed.", exception.getMessage());
    }

    //====== TESTs FOR ADDRESS ======/

    // Test if address is valid
    @Test
    public void testSetAdresseValid() {
        Client client = new Client();
        client.setAdresse("123 Main Street");
        assertEquals("123 main street", client.getAdresse());
    }

    // If address length exceeds 100 characters -> error
    @Test
    public void testSetAdresseInvalidLength() {
        Client client = new Client();
        String longAdresse = "a".repeat(101);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setAdresse(longAdresse));
        assertEquals("Address length must be less than or equal to 100 characters.", exception.getMessage());
    }

    //====== TESTs FOR PHONE NUMBER ======/

    // Test if phone number is valid with "+"
    @Test
    public void testSetPhoneNumberValidWithPlus() {
        Client client = new Client();
        client.setPhoneNumber("+12345678901");
        assertEquals("+12345678901", client.getPhoneNumber());
    }

    // Test if phone number is valid with "0"
    @Test
    public void testSetPhoneNumberValidWithZero() {
        Client client = new Client();
        client.setPhoneNumber("0123456789");
        assertEquals("0123456789", client.getPhoneNumber());
    }

    // If phone number has invalid format
    @Test
    public void testSetPhoneNumberInvalidFormat() {
        Client client = new Client();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setPhoneNumber("123456"));
        assertEquals("Invalid phone number format. Use + followed by 11 digits or 0 followed by 9 digits.", exception.getMessage());
    }

    //====== TESTs FOR EMAIL ======/

    // Test if email is valid
    @Test
    public void testSetEmailValid() {
        Client client = new Client();
        client.setEmail("some.text123@mail.com");
        assertEquals("some.text123@mail.com", client.getEmail());
    }

    // If email has invalid format
    @Test
    public void testSetEmailInvalidFormat() {
        Client client = new Client();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setEmail("badformat"));
        assertEquals("Invalid email format. The format should be: some.text123@mail.com", exception.getMessage());
    }

    //====== TESTs FOR CREDIT CARD ======/

    // Test if credit card number is valid
    @Test
    public void testSetCreditCardValid() {
        Client client = new Client();
        client.setCreditCard("1234567890123456");
        assertEquals("1234567890123456", client.getCreditCard());
    }

    // If credit card number has invalid format
    @Test
    public void testSetCreditCardInvalidFormat() {
        Client client = new Client();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setCreditCard("1234"));
        assertEquals("Invalid credit card format. Only 16 digits are allowed.", exception.getMessage());
    }

    //====== TESTs FOR PLATE NUMBERS ======/

    // Test if plate numbers are valid
    @Test
    public void testSetPlateNumbersValid() {
        Client client = new Client();
        client.setPlateNumbers("ABC123");
        assertEquals("ABC123", client.getPlateNumbers());
    }

    // If plate numbers have invalid format
    @Test
    public void testSetPlateNumbersInvalidFormat() {
        Client client = new Client();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setPlateNumbers("A1"));
        assertEquals("Invalid plate numbers format. The plate numbers must be exactly 6 characters long and contain only letters and digits.", exception.getMessage());
    }

    //====== TESTs FOR RESERVATION NUMBER ======/

    // Test if reservation number is valid
    @Test
    public void testSetReservationNumberValid() {
        Client client = new Client();
        client.setReservationNumber(123456);
        assertEquals(123456, client.getReservationNumber());
    }

    // If reservation number has invalid length
    @Test
    public void testSetReservationNumberInvalidLength() {
        Client client = new Client();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setReservationNumber(123));
        assertEquals("Reservation number must be a positive integer with exactly 6 digits.", exception.getMessage());
    }

    //====== TESTs FOR USERNAME ======/

    // Test if username is valid
    @Test
    public void testSetUsernameValid() {
        Client client = new Client();
        client.setUsername("username123");
        assertEquals("username123", client.getUsername());
    }

    // If username has invalid format
    @Test
    public void testSetUsernameInvalidFormat() {
        Client client = new Client();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setUsername("user@name"));
        assertEquals("Invalid username format. The username can only contain letters, digits, hyphens (-), underscores (_), and dots (.).", exception.getMessage());
    }

    //====== TESTs FOR PASSWORD ======/

    // Test if password is valid
    @Test
    public void testSetPasswordValid() {
        Client client = new Client();
        client.setPassword("securePassword123");
        assertEquals("securePassword123", client.getPassword());
    }

    // If password length exceeds 30 characters
    @Test
    public void testSetPasswordInvalidLength() {
        Client client = new Client();
        String longPassword = "a".repeat(31);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> client.setPassword(longPassword));
        assertEquals("Invalid password format. The password can only contain letters and digits. The length should equal or bigger then 8.", exception.getMessage());
    }
}