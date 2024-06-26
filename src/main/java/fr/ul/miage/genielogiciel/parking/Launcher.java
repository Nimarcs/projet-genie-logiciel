package fr.ul.miage.genielogiciel.parking;


import fr.ul.miage.genielogiciel.parking.commandLine.CommandLine;

import java.util.Scanner;

public class Launcher {


    /**
     * Entry point for the FastBorne application.
     * <p>
     * This method initializes the CommandLine object and starts the application by calling the run method.
     * </p>
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        FacadeInterface facadeInterface = new FacadeInterface();
        facadeInterface.initializeParking();

        CommandLine commandLine = new CommandLine();
        commandLine.run(facadeInterface);
    }


}
