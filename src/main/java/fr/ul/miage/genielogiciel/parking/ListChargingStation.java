package fr.ul.miage.genielogiciel.parking;

import java.util.ArrayList;
import java.util.List;

public class ListChargingStation {

    private List<ChargingStation> stations;

    public ListChargingStation () {
        stations = new ArrayList<>();
    }

    public void addStation(ChargingStation station) {
        stations.add(station);
    }


    public ChargingStation findChargingStation(int idStation){
        for (ChargingStation station: stations){
            if (station.getIdStation() == idStation) return station;
        }
        return null;
    }
}
