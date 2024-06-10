package fr.ul.miage.genielogiciel.parking.commandLine;

import org.apache.commons.lang3.StringUtils;

/**
 * Manage the pure display of the command line
 */
public class DisplayerSout implements Displayer {

    private int headerWidth;

    public DisplayerSout(){
        headerWidth = 30;
    }

    /**
     * Display a line inside the command line display
     * @param message message to display
     */
    @Override
    public void displayMessage(String message){
        System.out.println(message);
    }

    /**
     * Display an important message
     *
     * @param message message to display
     */
    @Override
    public void displayImportantMessage(String message) {
        System.out.printf("--- %s ---%n", message);
    }

    /**
     * Display an error message
     *
     * @param message message to display
     */
    @Override
    public void displayErrorMessage(String message) {
        System.out.println("/!\\ " + message);
    }

    /**
     * Display a header for information
     *
     * @param headerName name of the header
     */
    @Override
    public void displayHeader(String headerName) {
        //We change the width of the menu if necessary
        if (headerName.length() + 6 > headerWidth) headerWidth = headerName.length() + 6;

        System.out.println(StringUtils.rightPad("+", headerWidth - 1, "-") + "+");
        System.out.println(StringUtils.center(StringUtils.center(headerName, headerWidth - 2), headerWidth, "|"));
        System.out.println(StringUtils.rightPad("+", headerWidth - 1, "-") + "+");
    }

    /**
     * Display a menu inside the command line display
     * @param options options to display
     * @param menuName name of the menu
     */
    @Override
    public void displayMenu(String[] options, String menuName){

        //Put the header
        displayHeader(menuName);

        //Display all the option
        for (int i = 0; i < options.length; i++) {
            System.out.println(StringUtils.center(StringUtils.center((i+1) + " - " + options[i], headerWidth - 2), headerWidth, "|"));
        }

        //End the box
        System.out.println(StringUtils.rightPad("+", headerWidth - 1, "-") + "+");
    }

}
