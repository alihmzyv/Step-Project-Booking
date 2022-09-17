package helpers;

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

    static String getString(File textFile) {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(textFile))) {
            br.lines()
                    .forEach(line -> sb.append(line).append("\n"));
        }
        catch (FileNotFoundException exc) {
            System.out.printf("Could not find menu text file at:\n%s\n", textFile.getAbsolutePath());
            System.exit(1);
        }
        catch (IOException exc) {
            System.out.printf("Could not read menu text file at:\n%s\n", textFile.getAbsolutePath());
            System.exit(1);
        }
        return sb.toString();
    }

    static boolean isStrongPassword(String password) {
        return Pattern.matches("^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", password);
    }
}
