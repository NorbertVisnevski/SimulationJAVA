package com.NV.simulation.managers.file;

import java.io.File;

public interface AsyncFileHandler {

    void writeAsync(File file);

    void readAsync(File file);
}
