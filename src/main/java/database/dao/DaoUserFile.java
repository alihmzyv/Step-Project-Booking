package database.dao;

import entities.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class DaoUserFile extends DaoFile<User> {
    public DaoUserFile(File file) {
       super(file);
    }
}
