package helpers;

import exceptions.booking_menu_exceptions.FileDatabaseException;

import java.io.*;
import java.time.Duration;
import java.util.regex.Pattern;

public interface Helper {
    static String getHumanReadableDuration(Duration duration) {
        return duration.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .toLowerCase();
    }

    static String readTextFile(File textFile) {
        try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            StringBuilder sb = new StringBuilder();
            br.lines()
                    .forEach(line -> sb.append(line).append("\n"));
            return sb.toString();
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    static boolean isStrongPassword(String password) {
        return Pattern.matches("^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", password);
    }
}
