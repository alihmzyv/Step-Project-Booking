package menu_items;

public class EndSession extends MenuItem implements Runnable {


    public EndSession(int id) {
        super(id);
    }

    @Override
    public void run() {
        getDatabase().updateLocal();
    }
}
