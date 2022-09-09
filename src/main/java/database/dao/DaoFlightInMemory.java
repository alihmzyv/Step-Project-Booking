package database.dao;

import entities.Flight;

import java.util.List;

public class DaoFlightInMemory extends DaoInMemory<Flight> {
    public DaoFlightInMemory(List<Flight> flights) {
        super(flights);
    }
}
