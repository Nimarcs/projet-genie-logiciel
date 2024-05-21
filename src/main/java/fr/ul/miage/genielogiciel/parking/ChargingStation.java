public class ChargingStation {

    // === General info of charging station ===
    private int id;
    private boolean isDisponible;

    ChargingStation(int id, boolean isDisponible) {
        setId(id);
        setDispobnible(isDisponible);
    }

    //    === GETTERS and SETTERS ===
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public boolean getDisponible() { return disponible; }
    public void setDispobnible(boolean disponible) { isDisponible = disponible; }

}