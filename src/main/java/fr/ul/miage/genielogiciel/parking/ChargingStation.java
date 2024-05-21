package fr.ul.miage.genielogiciel.parking;

public class ChargingStation {

    // === General info of charging station ===
    private int idStation;
    private boolean isDisponible;

    ChargingStation(int idStation, boolean isDisponible) {
        setIdStation(idStation);
        setDispobnible(isDisponible);
    }

    //    === GETTERS and SETTERS ===
    public int getIdStation() { return idStation; }
    public void setIdStation(int id) { this.idStation = idStation; }


    public boolean getDisponible() { return isDisponible; }
    public void setDispobnible(boolean disponible) { isDisponible = disponible; }

}