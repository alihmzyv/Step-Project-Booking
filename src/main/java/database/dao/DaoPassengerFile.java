package database.dao;

import entities.Passenger;

import java.io.File;

public class DaoPassengerFile extends DaoFile<Passenger> {
    public DaoPassengerFile(File file) {
        super(file);
    }
}