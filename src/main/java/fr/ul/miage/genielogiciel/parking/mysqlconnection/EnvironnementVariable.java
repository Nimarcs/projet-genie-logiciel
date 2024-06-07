package fr.ul.miage.genielogiciel.parking.mysqlconnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnvironnementVariable {

    /**
     * Map of all the loaded env variables
     */
    private final Map<String, String> envVariable;

    /**
     * Contructor of Environnement Variable
     * You need to call load before using it
     */
    public EnvironnementVariable() {
        envVariable = new HashMap<>();
    }

    /**
     * Load the environnement variables from the .env file
     * @throws IOException in case of a failure in the reading
     */
    public void load() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("./.env"));
        String line = bufferedReader.readLine();
        while (line != null) {
            int indexOfEqual = line.indexOf('=');
            String key = line.substring(0, indexOfEqual);
            String value = line.substring(indexOfEqual + 1);
            envVariable.put(key, value);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
    }

    /**
     * Get the value corresponding to the key
     * The values need to be loaded first !
     * @param key key
     * @return value corresponding to the key
     */
    public String get(String key) {
        return envVariable.get(key);
    }

}
