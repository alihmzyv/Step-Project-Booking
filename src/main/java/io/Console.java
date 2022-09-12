package io;

import java.io.File;
import java.util.List;

public interface Console {
    void println(Object obj);
    void printf(String format, Object...args);
    void printAsTable(List<String> headings, List objects, int width);
    void printAsIndexedTable(List<String> headings, List objects, int width);
    void printInRow(List<String> headings, List objects, int width);
    String getInput(String message);
    int getPositiveInt(String message);
}
