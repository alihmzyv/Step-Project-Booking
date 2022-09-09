package io;

import java.io.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RealConsole implements Console {
    private final Scanner sn = new Scanner(System.in);

    @Override
    public void println(Object obj) {
        System.out.println(obj.toString());
    }

    @Override
    public void printFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.lines()
                    .forEach(this::println);
        }
        catch (FileNotFoundException e) {
            System.out.printf("Could not find the file: %s\n", file.getPath());
        }
        catch (IOException e) {
            System.out.printf("Could not read the file: %s\n", file.getPath());
        }
    }

    @Override
    public String getInput(String message) {
        String input;
        while (true) {
            try {
                input = sn.nextLine();
                return input;
            }
            catch (NoSuchElementException exc) {
                System.out.println("Please do not enter empty string. Try again.");
            }
        }
    }

    @Override
    public int getPositiveInt(String message) {
        int input;
        while (true) {
            try {
                input = sn.nextInt();
                if (input < 0) throw new InputMismatchException();
                sn.nextLine();
                return input;
            }
            catch (InputMismatchException exc) {
                System.out.println("Please enter a positive integer. Try again.");
            }
        }
    }
}
