package edu.ccrm.interfaces;

import java.util.List;

/**
 * Functional interface demonstrating generics and lambda usage
 */
@FunctionalInterface
public interface Searchable<T> {
    List<T> search(String keyword);
}