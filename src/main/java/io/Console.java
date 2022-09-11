package io;

import java.io.File;
import java.util.List;

public interface Console {
    void println(Object obj);
    <A> void printAsTable(List<String> headings, List<A> objects, int width);
    <A> void printAsIndexedTable(List<String> headings, List<A> objects, int width);
    String getInput(String message);
    int getPositiveInt(String message);
}
