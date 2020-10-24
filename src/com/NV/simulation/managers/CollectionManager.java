package com.NV.simulation.managers;

import java.util.Collection;
import java.util.List;

public interface CollectionManager<T> {

    void add(T object);

    void add(Collection<T> collection);

    List<T> getList();

    void update();

    void clear();
}
