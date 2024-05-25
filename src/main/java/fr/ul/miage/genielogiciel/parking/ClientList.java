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

   public Client findClientByLicense(String numberLicense){
       for (Client client: clients){
           if (client.getPlateNumbers().equals(numberLicense)) return client;
       }
       return null;
   }

//    public Client findClientByReservation(String numberReservation){
//        for (Client client: clients){
//            if (client.get) return client;
//        }
//        return null;
//    }

}
