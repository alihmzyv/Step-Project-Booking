package helpers;

import exceptions.database_exceptions.LocalDatabaseException;

import java.io.*;
import java.time.Duration;
import java.util.regex.Pattern;

public interface Helper {
    /*
     * returns the human-readable String representation of a Duration instance,
     * * e.g. :
     *   - 5h
     *   - 7h 15m
     */
    static String getHumanReadableDuration(Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

    /*
     * Reads the text file at the specified path and returns the text as a String
     * * textFile - the path to the text file
     * * throws LocalDatabaseException whose cause can be either FileNotFoundException,
     *   or IOException due to a problem occurred during reading.
     */
    static String readTextFile(File textFile) {
        try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            StringBuilder sb = new StringBuilder();
            br.lines()
                    .forEach(line -> sb.append(line).append("\n"));
            return sb.toString();
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    /*
     * Returns true if the specified String contains:
     * * at least 6 chars
     * * at least one digit
     * * at least one lower-case alphabetical character and one upper-case alphabetical character
     * * Contains at least one character within a set of special characters (@#$%^&+=)
     * * Does not contain space, tab, etc.
     * otherwise false.
     */
    static boolean isStrongPassword(String password) {
        return Pattern.matches("^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", password);
    }
}
