package com.epam.lab.comparator;

import com.epam.lab.model.News;

import java.io.Serializable;
import java.util.Comparator;

public class AuthorSurnameComparator implements Comparator<News>, Serializable {

    @Override
    public int compare(final News o1, final News o2) {
        return o1.getAuthor().getSurname().compareToIgnoreCase((o2.getAuthor().getSurname()));
    }
}
