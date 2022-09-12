package io;

import java.io.File;
import java.util.List;

public interface Console {
    void println(Object obj);
    void printf(String format, Object...args);
    void printAsTable(String title, List<String> headings, List objects, int width);
    void printNestedAsTable(String title, List<String> headings, List<List> objects, int width);
    void printAsIndexedTable(String title, List<String> headings, List objects, int width);
    void printInRow(List<String> headings, List objects, int width);
    String getInput(String message);
    int getPositiveInt(String message);
}
