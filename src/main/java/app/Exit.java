package app;

public class Exit extends MenuItem {
    private Database database;
    public Exit(int id) {
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
