package io;

import java.io.File;

public interface Console {
    void println(Object obj);

    void printFile(File file);
    String getInput(String message);
    int getPositiveInt(String message);
}
