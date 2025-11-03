package com.bookclub.service;

import java.util.List;

public interface GenericCrudDao<E, K> {

    // Create
    void add(E entity);

    // Update
    void update(E entity);

    // Delete
    boolean remove(String username, K key);  // delete by username + ID

    // Read all (for specific user)
    List<E> list(String username);

    // Read one
    E find(K key);
}


