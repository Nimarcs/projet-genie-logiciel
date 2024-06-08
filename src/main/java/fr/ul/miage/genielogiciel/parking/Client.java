package fr.ul.miage.genielogiciel.parking;

/**
 * Represents a client in the parking system
 *
 * @since 1.0
 */
public class Client {

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

    // MAX and MIN size for the parameters
    private final int MIN_SIZE_NAME = 2;
    private final int MAX_SIZE_NAME = 20;
    private final int MIN_SIZE_SURNAME = 2;
    private final int MAX_SIZE_SURNAME = 20;
    private final int MAX_SIZE_ADRESSE = 100;
    private final int MIN_SIZE_PHONE = 10;
    private final int MAX_SIZE_PHONE = 12;
    private final int MIN_SIZE_EMAIL = 5;
    private final int MAX_SIZE_EMAIL = 100;
    private final int SIZE_CREDIT_CARD = 16;
    private final int SIZE_PLATE_NUMBERS = 6;
    private final int SIZE_RESERVATION_NUMBER = 6;
    private final int MIN_SIZE_USERNAME = 1;
    private final int MAX_SIZE_USERNAME = 15;
    private final int MIN_SIZE_PASSWORD = 8;
    private final int MAX_SIZE_PASSWORD = 30;



    /**
     * Constructor for creating a new client with the specified details
     *
     * @param name first name
     * @param surname surname
     * @param adresse living address
     * @param phoneNumber phone number
     * @param email email address
     * @param creditCard credit card number
     * @param plateNumbers vehicle license plate numbers
     * @param username username for the system
     * @param password password for the system
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
        if (validateFormat(name, MIN_SIZE_NAME, MAX_SIZE_NAME, "[a-zA-Z]+")) {
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
        if (validateFormat(surname, MIN_SIZE_SURNAME, MAX_SIZE_SURNAME, "[a-zA-Z]+")) {
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
        if (adresse.length() <= MAX_SIZE_ADRESSE) {
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
        if (validateFormat(phoneNumber, MIN_SIZE_PHONE, MAX_SIZE_PHONE, "\\+\\d{11}|0\\d{9}")) {
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
        if (validateFormat(email, MIN_SIZE_EMAIL, MAX_SIZE_EMAIL, "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
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
        if (validateFormat(creditCard, SIZE_CREDIT_CARD, SIZE_CREDIT_CARD, "\\d{16}")) {
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
        if (validateFormat(plateNumbers, SIZE_PLATE_NUMBERS, SIZE_PLATE_NUMBERS, "[a-zA-Z0-9]{6}")) {
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
        if (validateFormat(username, MIN_SIZE_USERNAME, MAX_SIZE_USERNAME, "^[a-zA-Z0-9]+$")) {
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
        if (validateFormat(password, MIN_SIZE_PASSWORD, MAX_SIZE_PASSWORD, "^[a-zA-Z0-9]+$")) {
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
}
