package fr.ul.miage.genielogiciel.parking;

import java.util.ArrayList;
import java.util.List;

// list of charging stations

public class ListChargingStation {

    private List<ChargingStation> stations;

    public ListChargingStation () {
        stations = new ArrayList<>();
    }

    // add to the list charging station
    public void addStation(ChargingStation station) {
        stations.add(station);
    }


    // find charging station by id
    public ChargingStation findChargingStation(int idStation){
        for (ChargingStation station: stations){
            if (station.getIdStation() == idStation) return station;
        }
        return null;
    }

    // check if there is any available charging station
    public boolean isAnyStationAvailable() {
        for (ChargingStation station : stations) {
            if (station.getDisponible()) {
                return true;
            }
        }
        return false;
    }

    // get available charging station
    public List<ChargingStation> getAvailableStations() {
        List<ChargingStation> availableStations = new ArrayList<>();
        for (ChargingStation station : stations) {
            if (station.getDisponible()) {
                availableStations.add(station);
            }
        }
        return availableStations;
    }
}
