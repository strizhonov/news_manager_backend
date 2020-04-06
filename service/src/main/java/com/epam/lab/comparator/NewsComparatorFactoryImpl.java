package com.epam.lab.comparator;

import com.epam.lab.model.News;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

public class NewsComparatorFactoryImpl implements NewsComparatorFactory {

    private final Map<NewsSortType, Comparator<News>> registered = new EnumMap<>(NewsSortType.class);

    public NewsComparatorFactoryImpl() {
        registerComparators();
    }

    @Override
    public Comparator<News> get(final NewsSortType... types) {
        Comparator<News> result = empty();
        for (NewsSortType current : types) {
            result = result.thenComparing(registered.get(current));
        }
        return result;
    }

    /**
     * Register comparators that can be used for news sorting.
     * Every comparator corresponds to search parameter of API
     */
    private void registerComparators() {
        registered.put(NewsSortType.AUTHOR_SURNAME, new AuthorSurnameComparator());
        registered.put(NewsSortType.NUMBER_OF_TAGS, new NumberOfTagComparator());
        registered.put(NewsSortType.CREATION_DATE, new CreationDateComparator());
    }


    private Comparator<News> empty() {
        return (o1, o2) -> 0;
    }

}
