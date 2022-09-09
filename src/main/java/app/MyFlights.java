package app;

public class MyFlights extends MenuItem {
    private Database database;
    public MyFlights(int id) {
        super(id);
    }

    @Override
    public void run() {

    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
