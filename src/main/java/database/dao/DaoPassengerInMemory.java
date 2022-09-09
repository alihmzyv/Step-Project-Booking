package database.dao;

import entities.Passenger;

import java.util.List;

public class DaoPassengerInMemory extends DaoInMemory<Passenger> {

    public DaoPassengerInMemory(List<Passenger> passengers) {
        super(passengers);
    }
}
