package fr.ul.miage.genielogiciel.parking;

public class Client {
//    === Client's info ===
    private String name;
    private String surname;
    private String adresse;
    private String phoneNumber;
    private String email;
    private int creditCard;
    private String plateNumbers;

    public Client(String name, String surname, String adresse, String phoneNumber, String email, int creditCard, String plateNumbers) {
       setName(name);
       setSurname(surname);
       setAdresse(adresse);
       setPhoneNumber(phoneNumber);
       setEmail(email);
       setCreditCard(creditCard);
    }



//    === GETTERS and SETTERS ===
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() {return surname;}
    public void setSurname(String surname) { this.surname = surname; }

    public String getAdresse() {return adresse;}
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() {return email;}
    public void setEmail(String email) { this.email = email; }

    public int getCreditCard() {return creditCard;}
    public void setCreditCard(int creditCard) { this.creditCard = creditCard; }

    public String getPlateNumbers() { return plateNumbers; }
    public void setPlateNumbers(String plateNumbers) { this.plateNumbers = plateNumbers; }
}