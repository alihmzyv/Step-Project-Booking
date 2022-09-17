package io;

import java.io.File;
import java.util.List;

public interface Console {
    void println(Object obj);
    void printf(String format, Object...args);
    <A> void printAsTable(String title, List<String> headings, List<A> objects, int width);
    <A> void printNestedAsTable(String title, List<String> headings, List<List<A>> objects, int width);
    <A> void printAsIndexedTable(String title, List<String> headings, List<A> objects, int width);
    <A> void printInRow(List<String> headings, List<A> objects, int width);
    String getInput(String message);
    int getPositiveInt(String message);
}
