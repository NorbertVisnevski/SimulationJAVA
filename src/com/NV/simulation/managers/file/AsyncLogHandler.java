package com.NV.simulation.managers.file;

import java.io.File;

public interface AsyncLogHandler {

    void append(File file, String message);

    void clear(File file);

}
