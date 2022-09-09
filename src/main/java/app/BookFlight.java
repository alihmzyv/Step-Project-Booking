package app;

public class BookFlight extends MenuItem {
    private Database database;

    public BookFlight(int id) {
        super(id);
    }

    @Override
    public void run() {

    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
