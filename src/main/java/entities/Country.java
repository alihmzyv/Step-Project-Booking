package entities;

public enum Country {
    AZERBAIJAN,
    TURKEY;

    @Override
    public String toString() {
        return Character.toLowerCase(name().charAt(0)) + name().substring(1);
    }
}
