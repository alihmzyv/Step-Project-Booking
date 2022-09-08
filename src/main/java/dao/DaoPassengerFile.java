package dao;

import entities.Passenger;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class DaoPassengerFile extends DaoFile<Passenger> {
    public DaoPassengerFile(File file) {
        super(file);
    }
}