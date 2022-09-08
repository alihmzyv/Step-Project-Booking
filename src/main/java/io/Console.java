package io;

import java.io.File;

public interface Console {
    void println(String str);

    void printFile(File file);
    String getInput(String message);
    int getPositiveInt(String message);
}
