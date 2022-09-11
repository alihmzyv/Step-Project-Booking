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
    public <A> void printAsTable(List<String> headings, List<A> objects, int width) {
        System.out.println("\t".repeat(3) + "=".repeat(width));
        System.out.print("\t".repeat(3));
        System.out.println(String.join(" || ", headings));
        System.out.print("\t".repeat(3));
        System.out.println("-".repeat(width));
        objects.stream().forEach(obj -> {
            System.out.print("\t".repeat(3));
            System.out.println(obj);
        });
        System.out.println("\t".repeat(3) + "=".repeat(width));
    }

    @Override
    public <A> void printAsIndexedTable(List<String> headings, List<A> objects, int width) {
        headings.add(0, "ID");
        System.out.println("\t".repeat(3) + "=".repeat(width));
        System.out.print("\t".repeat(3));
        System.out.println(String.join(" || ", headings));
        System.out.print("\t".repeat(3));
        System.out.println("-".repeat(width));
        int[] indexCounter = {1};
        objects.stream().forEach(obj -> {
            System.out.print("\t".repeat(3));
            System.out.print(indexCounter[0]++ + "  || ");
            System.out.println(obj);
        });
        System.out.println("\t".repeat(3) + "=".repeat(width));
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
