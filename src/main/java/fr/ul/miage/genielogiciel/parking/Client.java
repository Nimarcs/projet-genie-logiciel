package fr.ul.miage.genielogiciel.parking;


import java.util.Objects;

/**
 * Represents a client in the parking system
 *
 * @since 1.0
 */
public class Client {

    // REGEX that the value need to match
    public static final String NAME_REGEX = "[a-zA-Z]+";
    public static final String SURNAME_REGEX = "[a-zA-Z]+";
    public static final String ADDRESS_REGEX = ".*"; // Any
    public static final String PHONE_NUMBER_REGEX = "\\+\\d{11}|0\\d{9}";
    public static final String EMAIL_REGEX = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    public static final String CREDIT_CARD_REGEX = "\\d{16}";
    public static final String PLATE_NUMBER_REGEX = "[a-zA-Z0-9]{6}";
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9]+$";
    public static final String PASSWORD_REGEX = "^[a-zA-Z0-9]+$";

    // MAX and MIN size for the parameters
    public static final int MAX_SIZE_PASSWORD = 30;
    public static final int MIN_SIZE_NAME = 2;
    public static final int MAX_SIZE_NAME = 20;
    public static final int MIN_SIZE_SURNAME = 2;
    public static final int MAX_SIZE_SURNAME = 20;
    public static final int MIN_SIZE_ADRESSE = 0;
    public static final int MAX_SIZE_ADRESSE = 100;
    public static final int MIN_SIZE_PHONE = 10;
    public static final int MAX_SIZE_PHONE = 12;
    public static final int MIN_SIZE_EMAIL = 5;
    public static final int MAX_SIZE_EMAIL = 100;
    public static final int SIZE_CREDIT_CARD = 16;
    public static final int SIZE_PLATE_NUMBERS = 6;
    public static final int SIZE_RESERVATION_NUMBER = 6;
    public static final int MIN_SIZE_USERNAME = 1;
    public static final int MAX_SIZE_USERNAME = 15;
    public static final int MIN_SIZE_PASSWORD = 8;

    // Client's parameters
    private String name;
    private String surname;
    private String adresse;
    private String phoneNumber;
    private String email;
    private String creditCard;
    private String plateNumbers;
    private int reservationNumber;
    private String username;
    private String password;




    /**
     * Constructor for creating a new client with the specified details
     * (using for registration of new clients)
     * @param name the client's first name
     * @param surname the client's surname
     * @param adresse the client's address
     * @param phoneNumber the client's phone number
     * @param email the client's email address
     * @param creditCard the client's credit card number
     * @param plateNumbers the client's vehicle license plate numbers
     * @param username the client's username for the system
     * @param password the client's password for the system
     */
    public Client(String name, String surname, String adresse, String phoneNumber, String email, String creditCard, String plateNumbers, String username, String password) {
        setName(name);
        setSurname(surname);
        setAdresse(adresse);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setCreditCard(creditCard);
        setPlateNumbers(plateNumbers);
        setUsername(username);
        setPassword(password);
    }

    /**
     * Default constructor for creating an empty client
     */
    public Client() {}

    /**
     * Gets the client's first name
     *
     * @return the client's first name
     */
    public String getName() { return name; }

    /**
     * Gets the client's surname
     *
     * @return the client's surname
     */
    public String getSurname() { return surname; }

    /**
     * Gets the client's address
     *
     * @return the client's address
     */
    public String getAdresse() { return adresse; }

    /**
     * Gets the client's phone number
     *
     * @return the client's phone number
     */
    public String getPhoneNumber() { return phoneNumber; }

    /**
     * Gets the client's email address
     *
     * @return the client's email address
     */
    public String getEmail() { return email; }

    /**
     * Gets the client's credit card number
     *
     * @return the client's credit card number
     */
    public String getCreditCard() { return creditCard; }

    /**
     * Gets the client's vehicle license plate numbers
     *
     * @return the client's vehicle license plate numbers
     */
    public String getPlateNumbers() { return plateNumbers; }

    /**
     * Gets the client's reservation number
     *
     * @return the client's reservation number
     */
    public int getReservationNumber() { return reservationNumber; }

    /**
     * Gets the client's username for the system
     *
     * @return the client's username
     */
    public String getUsername() { return username; }

    /**
     * Gets the client's password for the system
     *
     * @return the client's password
     */
    public String getPassword() { return password; }

    /**
     * Sets the client's first name
     *
     * @param name the new first name
     */
    public void setName(String name) {
        if (validateFormat(name, MIN_SIZE_NAME, MAX_SIZE_NAME, NAME_REGEX)) {
            this.name = capitalizeFirstLetter(name);
        } else {
            throw new IllegalArgumentException("Invalid name format. Only letters are allowed.");
        }
    }

    /**
     * Sets the client's surname
     *
     * @param surname the new surname
     */
    public void setSurname(String surname) {
        if (validateFormat(surname, MIN_SIZE_SURNAME, MAX_SIZE_SURNAME, SURNAME_REGEX)) {
            this.surname = capitalizeFirstLetter(surname);
        } else {
            throw new IllegalArgumentException("Invalid surname format. Only letters are allowed.");
        }
    }

    /**
     * Sets the client's address
     *
     * @param adresse the new address
     */
    public void setAdresse(String adresse) {
        if (validateFormat(adresse, MIN_SIZE_ADRESSE, MAX_SIZE_ADRESSE, ADDRESS_REGEX)) {
            this.adresse = capitalizeFirstLetter(adresse);
        } else {
            throw new IllegalArgumentException("Address length must be less than or equal to 100 characters.");
        }
    }

    /**
     * Sets the client's phone number
     *
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        if (validateFormat(phoneNumber, MIN_SIZE_PHONE, MAX_SIZE_PHONE, PHONE_NUMBER_REGEX)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number format. Use + followed by 11 digits or 0 followed by 9 digits.");
        }
    }

    /**
     * Sets the client's email address
     *
     * @param email the new email address
     */
    public void setEmail(String email) {
        if (validateFormat(email, MIN_SIZE_EMAIL, MAX_SIZE_EMAIL, EMAIL_REGEX)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format. The format should be: some.text123@mail.com");
        }
    }

    /**
     * Sets the client's credit card number
     *
     * @param creditCard the new credit card number
     */
    public void setCreditCard(String creditCard) {
        if (validateFormat(creditCard, SIZE_CREDIT_CARD, SIZE_CREDIT_CARD, CREDIT_CARD_REGEX)) {
            this.creditCard = creditCard;
        } else {
            throw new IllegalArgumentException("Invalid credit card format. Only 16 digits are allowed.");
        }
    }

    /**
     * Sets the client's vehicle license plate numbers
     *
     * @param plateNumbers the new vehicle license plate numbers
     */
    public void setPlateNumbers(String plateNumbers) {
        if (validateFormat(plateNumbers, SIZE_PLATE_NUMBERS, SIZE_PLATE_NUMBERS, PLATE_NUMBER_REGEX)) {
            this.plateNumbers = plateNumbers.toUpperCase();
        } else {
            throw new IllegalArgumentException("Invalid plate numbers format. The plate numbers must be exactly 6 characters long and contain only letters and digits.");
        }
    }

    /**
     * Sets the client's reservation number
     *
     * @param reservationNumber the new reservation number
     */
    public void setReservationNumber(int reservationNumber) {
        if (String.valueOf(reservationNumber).length() == SIZE_RESERVATION_NUMBER) {
            this.reservationNumber = reservationNumber;
        } else {
            throw new IllegalArgumentException("Reservation number must be a positive integer with exactly 6 digits.");
        }
    }

    /**
     * Sets the client's username for the system
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        if (validateFormat(username, MIN_SIZE_USERNAME, MAX_SIZE_USERNAME, USERNAME_REGEX)) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Invalid username format. The username can only contain letters, digits, hyphens (-), underscores (_), and dots (.).");
        }
    }

    /**
     * Sets the client's password for the system
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        if (validateFormat(password, MIN_SIZE_PASSWORD, MAX_SIZE_PASSWORD, PASSWORD_REGEX)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Invalid password format. The password can only contain letters and digits. The length should equal or bigger then 8.");
        }
    }

    /**
     * Validates the value against the provided constraints and returns true if valid
     *
     * @param value the value to validate
     * @param minLength the minimum length of the value
     * @param maxLength the maximum length of the value
     * @param regex the regular expression to validate the value against
     * @return true if the value is valid, false otherwise
     */
    private boolean validateFormat(String value, int minLength, int maxLength, String regex) {
        return value != null && value.length() >= minLength && value.length() <= maxLength && value.matches(regex);
    }

    /**
     * Capitalizes the first letter of the given string and makes the rest of the letters lowercase.
     *
     * @param input the input string
     * @return the formatted string
     */
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(phoneNumber, client.phoneNumber) || Objects.equals(email, client.email) || Objects.equals(username, client.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
