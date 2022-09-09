package app;

import controllers.FlightController;

public class DisplaySpecificFlight extends MenuItem {
    private Database database;
    public DisplaySpecificFlight(int id) {
        super(id);
    }

    @Override
    public void run() {
        throw new RuntimeException("not impl");
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
