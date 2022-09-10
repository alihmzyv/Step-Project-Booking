package entities;

public enum City {
    BAKU,
    ISTANBUL;

    @Override
    public String toString() {
        return Character.toLowerCase(name().charAt(0)) + name().substring(1);
    }
}
