package com.NV.simulation.threads;

public final class ThreadControlFlags {

    public volatile boolean frameRendered = true;

    public volatile int simulationSpeed;

    private static volatile ThreadControlFlags instance;

    public static ThreadControlFlags getInstance()
    {
        if(instance == null)
        {
            synchronized(ThreadControlFlags.class)
            {
                if(instance == null)
                instance = new ThreadControlFlags();
            }
        }
        return instance;
    }

    private ThreadControlFlags(){};
}
