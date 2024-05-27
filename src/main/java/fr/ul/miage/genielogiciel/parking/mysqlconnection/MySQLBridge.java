package fr.ul.miage.genielogiciel.parking.mysqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MySQLBridge {

    /**
     * Logger of the class
     */
    private static final Logger LOG = Logger.getLogger(MySQLBridge.class.getName());

    /**
     * Connection to the MySQL database
     * Need to be initialised with initConnection()
     */
    private Connection connection;

    /**
     * Create the bridge with no connection
     */
    public MySQLBridge() {
        connection = null;
    }

    /**
     * Initialise the connection with the database
     * @return boolean at true if the connection creation is done, false if something happened
     */
    public boolean initConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?user=genielogiciel&password=parkinggenie");
            return true;
        } catch (SQLException e) {
            LOG.severe(e.getMessage());
            return false;
        }
    }

    /**
     * Getter to check if the connection is set
     * @return true if the connection is set
     */
    public boolean isConnectionSet() {
        return connection != null;
    }

    /**
     * Getter de connection
     * It is needed to have the connection initialised before calling this getter
     *
     * @return connection of the bridge
     * @throws IllegalStateException sent if the connection is not set yet
     */
    public Connection getConnection() {
        if (connection == null)
            throw new IllegalStateException("The connection is not set yet, call initConnection before");
        return connection;
    }

    /**
     * Reset the connection or init the connection if there is no working connection
     * @return boolean at true if the connection is reset, false if something happened
     */
    public boolean resetConnection(){
        try {
            //If there is no connection yet
            if (!isConnectionSet() || connection.isClosed()) initConnection();
            else { //Else we close the current one and create a new one
                connection.close();
                initConnection();
            }
            return true;
        } catch (SQLException e) {
            LOG.severe(e.getMessage());
            return false;
        }

    }

}
