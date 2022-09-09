package app;

public abstract class MenuItem implements Runnable{
    final int id;

    public MenuItem(int id) {
        this.id = id;
    }

    public abstract void setDatabase(Database database);
}
