package database.dao;

import entities.User;

import java.io.File;

public class DaoUserFile extends DaoFile<User> {
    public DaoUserFile(File file) {
       super(file);
    }
}
