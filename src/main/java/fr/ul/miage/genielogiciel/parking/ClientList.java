package fr.ul.miage.genielogiciel.parking;
import java.util.List;
import java.util.ArrayList;

public class ClientList {

   private List<Client> clients;

   public ClientList () {
       clients = new ArrayList<>();
   }

   public void addClient(Client client) {
       clients.add(client);
   }


   public Client findClientByUsername(String username){
       for (Client client: clients){
           if (client.getUsername().equals(username)) return client;
       }
       return null;
   }


    public Client findClientByLicense(String license){
        for (Client client: clients){
            if (client.getPlateNumbers().equals(license)) return client;
        }
        return null;
    }

    public Client findByMobilePhone(String phoneNumber) {
        String normalizedPhoneNumber = normalizePhoneNumber(phoneNumber);
        for (Client client : clients) {
            if (client.getPhoneNumber().equals(normalizedPhoneNumber)) {
                return client;
            }
        }
        return null;
    }

    private String normalizePhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("[^\\d+]", "");
    }





}
