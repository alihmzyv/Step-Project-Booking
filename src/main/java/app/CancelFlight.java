package app;

public class CancelFlight extends MenuItem {

    private Database database;

    public CancelFlight(int id) {
        super(id);
    }

    @Override
    public void run() {

    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
