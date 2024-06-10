package fr.ul.miage.genielogiciel.parking.commandLine;

/**
 * Manage to display something to the user
 */
public interface Displayer {

    /**
     * Display a message
     *
     * @param message message to display
     */
    void displayMessage(String message);

    /**
     * Display an important message
     * @param message message to display
     */
    void displayImportantMessage(String message);

    /**
     * Display an error message
     * @param message message to display
     */
    void displayErrorMessage(String message);

    /**
     * Display a header for information
     * @param headerName name of the header
     */
    void displayHeader(String headerName);

    /**
     * Display a menu
     *
     * @param options  options to display
     * @param menuName name of the menu
     */
    void displayMenu(String[] options, String menuName);
}
