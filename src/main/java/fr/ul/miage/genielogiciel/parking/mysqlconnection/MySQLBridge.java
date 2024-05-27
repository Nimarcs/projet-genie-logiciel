package fr.ul.miage.genielogiciel.parking.mysqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLBridge {

    Connection connection;

    public MySQLBridge(){
        connection = null;
    }

    public void initConnection() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=root");
        //TODO work with that connection (and check if it works)
    }


}
