package com.NV.simulation.managers;

import java.io.File;
import java.util.concurrent.Future;

public interface AsyncFileHandler {

    void writeAsync(File file);

    void readAsync(File file);
}
