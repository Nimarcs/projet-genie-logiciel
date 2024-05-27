package fr.ul.miage.genielogiciel.parking.mysqlconnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class TestMySQLBridge {

    MySQLBridge mySQLBridge;

    @BeforeEach
    public void setupMySQLBridge() {
        mySQLBridge = new MySQLBridge();
    }

    @Test
    @DisplayName("MySQLBridge initConnection normal works ?")
    public void testInitConnection() {
        mySQLBridge.initConnection();
        assertTrue(mySQLBridge.isConnectionSet());
        assertDoesNotThrow(() -> mySQLBridge.getConnection());
    }

    @Test
    @DisplayName("MySQLBridge isConnectionSet() correct ?")
    public void testIsConnectionSet() {
        assertFalse(mySQLBridge.isConnectionSet());
        mySQLBridge.initConnection();
        assertTrue(mySQLBridge.isConnectionSet());
        assertDoesNotThrow(() -> mySQLBridge.getConnection());
        assertNotNull(mySQLBridge.getConnection());
    }

    @Test
    @DisplayName("MySQLBridge getConnection not init throw error ?")
    public void testGetConnectionNotInit() {
        assertFalse(mySQLBridge.isConnectionSet());
        assertThrows(IllegalStateException.class, () -> mySQLBridge.getConnection());
    }

    @Test
    @DisplayName("MySQLBridge getConnection give correct Connection")
    public void testGetConnectionGiveConnection() {
        mySQLBridge.initConnection();
        Connection connection = mySQLBridge.getConnection();
        assertDoesNotThrow(() -> {
            Statement statement = connection.createStatement();
            statement.executeQuery("select 1 from parking.client");
            statement.executeQuery("select 1 from parking.charging_station");
            statement.executeQuery("select 1 from parking.immatriculation_plate");
            statement.executeQuery("select 1 from parking.reservation");
        });
        assertDoesNotThrow(connection::close);
    }
}
