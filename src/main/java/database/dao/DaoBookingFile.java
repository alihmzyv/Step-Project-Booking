package database.dao;

import entities.Booking;

import java.io.File;

public class DaoBookingFile extends DaoFile<Booking> {
    private File file;
    public DaoBookingFile(File file) {
        super(file);
    }
}
