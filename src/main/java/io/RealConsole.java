package io;

import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RealConsole implements Console {
    private final Scanner sn = new Scanner(System.in);

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void printf(String format, Object...args) {
        System.out.printf(format, args);
    }

    @Override
    public void printAsTable(List<String> headings, List objects, int width) {
        printTableHeading(headings, width);
        objects.forEach(obj -> printf("%s%s\n", "\t".repeat(3), obj));
        printThickLine(width);
    }

    @Override
    public void printAsIndexedTable(List<String> headings, List objects, int width) {
        headings.add(0, "ID");
        printTableHeading(headings, width);
        int[] indexCounter = {1};
        objects.forEach(obj -> printf("%s%s%s%s\n","\t".repeat(3), indexCounter[0]++, " || ", obj));
        printThinLine(width);
    }

    @Override
    public void printInRow(List<String> headings, List fields, int width) {
        List<String> stringOfFields = fields.stream().map(field -> field.toString()).toList();
        printTableHeading(headings, width);
        println(String.join(" || ", stringOfFields));
        printf("%s%s\n", "\t".repeat(3), "=".repeat(width));
    }

    private void printTableHeading(List<String> headings, int width) {
        printThickLine(width);
        printf("%s%s\n", "\t".repeat(3), String.join(" || ", headings));
        printThinLine(width);
    }

    private void printThickLine(int width) {
        printf("%s%s\n","\t".repeat(3), "=".repeat(width));
    }

    private void printThinLine(int width) {
        printf("%s%s\n","\t".repeat(3), "-".repeat(width));
    }


    @Override
    public String getInput(String message) {
        println(message);
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
        println(message);
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
                sn.nextLine();
            }
        }
    }
}
