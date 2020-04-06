package com.epam.lab.comparator;

import com.epam.lab.model.News;

import java.io.Serializable;
import java.util.Comparator;

public class CreationDateComparator implements Comparator<News>, Serializable {

    @Override
    public int compare(final News o1, final News o2) {
        return o1.getCreationDate().compareTo(o2.getCreationDate());
    }
}
