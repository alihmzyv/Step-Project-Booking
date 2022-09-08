package dao;

import entities.Passenger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DaoPassengerInMemory extends DaoInMemory<Passenger> {

    public DaoPassengerInMemory(List<Passenger> passengers) {
        super(passengers);
    }
}
