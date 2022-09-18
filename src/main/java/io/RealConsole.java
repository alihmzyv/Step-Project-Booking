package io;

import java.io.*;
import java.util.*;

public class RealConsole implements Console {
    private final Scanner sn = new Scanner(System.in);
    private final PrintStream out = System.out;
    @Override
    public void println(Object obj) {
        out.println(obj);
    }

    @Override
    public void printf(String format, Object...args) {
        out.printf(format, args);
    }

    @Override
    public <A> void printAsTable(String title, List<String> headings, List<A> objects, int width) {
        printThickLine(width);
        printTitle(title, width);
        printTableHeading(headings, width);
        objects.forEach(obj -> println("\t".repeat(3) + obj));
        printThickLine(width);
    }

    @Override
    public void printAsRow(String title, List<String> headings, List<Object> objects, int width) {
        printThickLine(width);
        printTitle(title, width);
        printTableHeading(headings, width);
        println("\t".repeat(3) + String.join(" || ", objects.stream().map(Object::toString).toList()));
        printThickLine(width);
    }

    @Override
    public <A> void printNestedListAsTable(String title, List<String> headings, List<List<A>> objects, int width) {
        printThickLine(width);
        printTitle(title, width);
        printTableHeading(headings, width);
        objects.forEach(list -> {
            list.forEach(obj -> println("\t".repeat(3) + obj));
            printThinLine(width);
        });
        printThickLine(width);
    }

    @Override
    public <A> void printAsIndexedTable(String title, List<String> headings, List<A> objects, int width) {
        printThickLine(width);
        printTitle(title, width);
        headings = new ArrayList<>(headings);
        headings.add(0, "ID");
        printTableHeading(headings, width);
        int[] indexCounter = {1};
        objects.forEach(obj -> printf("%s || %s\n", "\t".repeat(3) + indexCounter[0]++, obj));
        printThickLine(width);
    }

    private void printTitle(String title, int width) {
        printf("%s%s\n", "\t".repeat(width / 8), title);
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
    public String getStringInput(String message) {
        println(message);
        while (true) {
            try {
                return sn.nextLine().strip();
            }
            catch (NoSuchElementException exc) {
                println("Please do not enter empty string. Try again.");
            }
        }
    }

    @Override
    public int getPositiveInt(String message) {
        println(message);
        while (true) {
            try {
                int input = sn.nextInt();
                if (input <= 0) throw new InputMismatchException();
                sn.nextLine();
                return input;
            }
            catch (InputMismatchException exc) {
                println("Please enter a positive integer. Try again.");
                sn.nextLine();
            }
        }
    }
}
