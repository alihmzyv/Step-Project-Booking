package dao;

import entities.Booking;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class DaoBookingFile extends DaoFile<Booking> {
    private File file;
    public DaoBookingFile(File file) {
        super(file);
    }
}
