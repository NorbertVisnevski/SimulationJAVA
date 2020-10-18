package com.NV.simulation.managers;

import java.io.File;

public interface AsyncFileHandler {

    void writeAsync(File file);

    void readAsync(File file);
}
