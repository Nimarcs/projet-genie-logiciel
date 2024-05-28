package fr.ul.miage.genielogiciel.parking;


/**
 * Represents a client in the parking system.
 *
 * @since 1.0
 */


public class Client {

    /**
     * Client's personal information
     */

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
    public Client(){}




//    === GETTERS and SETTERS ===

    /**
     * Gets the client's first name.
     *
     * @return the client's first name
     */
    public String getName() { return this.name; }

    /**
     * Sets the client's first name.
     *
     * @param name the new first name
     */
    public void setName(String name) { this.name = name; }



    public String getSurname() {return surname;}
    public void setSurname(String surname) { this.surname = surname; }

    public String getAdresse() {return adresse;}
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() {return email;}
    public void setEmail(String email) { this.email = email; }

    public String getCreditCard() {return creditCard;}
    public void setCreditCard(String creditCard) { this.creditCard = creditCard; }

    public String getPlateNumbers() { return plateNumbers; }
    public void setPlateNumbers(String plateNumbers) { this.plateNumbers = plateNumbers; }

    public int getReservationNumber() { return reservationNumber; }
    public void setReservationNumber(int reservationNumber) { this.reservationNumber = reservationNumber; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}