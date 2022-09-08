package dao;

import entities.Flight;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class DaoFlightFile extends DaoFile<Flight> {
    public DaoFlightFile(File file) {
        super(file);
    }
}
