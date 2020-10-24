package com.NV.simulation.managers;

import java.util.Collection;

public interface CollectionManager<T> {

    void add(T object);

    void add(Collection<T> collection);

    void update();

    void clear();
}
