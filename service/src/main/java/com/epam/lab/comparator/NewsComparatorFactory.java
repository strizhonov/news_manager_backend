package com.epam.lab.comparator;

import com.epam.lab.model.News;

import java.util.Comparator;

/**
 * Create and provide requesting side with NewsComparator object
 */
@FunctionalInterface
public interface NewsComparatorFactory {

    /**
     * Return NewsDto comparator retrieved from String
     *
     * @param types to comparator create from
     * @return newly created news comparator
     */
    Comparator<News> get(NewsSortType... types);

}
