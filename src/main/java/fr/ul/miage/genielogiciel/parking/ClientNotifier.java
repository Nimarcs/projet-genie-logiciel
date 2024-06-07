package fr.ul.miage.genielogiciel.parking;

public class ClientNotifier {


    /**
     * Sends a notification to a client.
     *
     * @param client the client to send the notification to
     * @param message the message to send
     */
    public void sendNotification(Client client, String message) {
        System.out.println("Notification to " + client.getPhoneNumber() + ": " + message);
    }

}
