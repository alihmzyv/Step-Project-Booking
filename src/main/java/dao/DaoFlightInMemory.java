package dao;

import entities.Flight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DaoFlightInMemory extends DaoInMemory<Flight> {



    public DaoFlightInMemory(List<Flight> flights) {
        super(flights);
    }
}
