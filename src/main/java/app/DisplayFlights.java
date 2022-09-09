package app;

import controllers.FlightController;

public class DisplayFlights extends MenuItem {
    private Database database;
    public DisplayFlights(int id) {
        super(id);
    }

    @Override
    public void run() {
        throw new RuntimeException();
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
