package fr.ul.miage.genielogiciel.parking.mysqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MySQLBridge {

    private static final Logger LOG = Logger.getLogger(MySQLBridge.class.getName());

    Connection connection;

    public MySQLBridge() {
        connection = null;
    }

    public void initConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking?user=genielogiciel&password=parkinggenie");
        } catch (SQLException e) {
            LOG.severe(e.getMessage());
            //TODO manage exception
        }
    }

    public boolean isConnectionSet() {
        return connection != null;
    }

    /**
     * Getter de connection
     *
     * @return connection of the bridge
     * @throws IllegalStateException sent if the connection is not set yet
     */
    public Connection getConnection() {
        if (connection == null)
            throw new IllegalStateException("The connection is not set yet, call initConnection before");
        return connection;
    }


}
