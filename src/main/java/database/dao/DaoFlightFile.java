package database.dao;

import entities.Flight;

import java.io.File;

public class DaoFlightFile extends DaoFile<Flight> {
    public DaoFlightFile(File file) {
        super(file);
    }
}
