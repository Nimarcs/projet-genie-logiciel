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



}
