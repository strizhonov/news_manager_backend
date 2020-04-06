package com.epam.lab.comparator;

import com.epam.lab.model.News;

import java.io.Serializable;
import java.util.Comparator;

public class NumberOfTagComparator implements Comparator<News>, Serializable {

    @Override
    public int compare(final News o1, final News o2) {
        return Integer.compare(o1.getTags().size(), o2.getTags().size());
    }
}
