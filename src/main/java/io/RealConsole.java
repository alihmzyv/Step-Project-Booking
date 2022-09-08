package io;

import java.io.*;
import java.util.Scanner;

public class RealConsole implements Console {
    private final Scanner sn = new Scanner(System.in);

    @Override
    public void println(String str) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public void printFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines()
                    .forEach(this::println);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getInput(String message) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public int getPositiveInt(String message) {
        throw new RuntimeException("not impl");
    }
}
