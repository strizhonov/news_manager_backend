package com.epam.lab.repository;

import com.epam.lab.model.News;
import com.epam.lab.search.NewsSearchQueryCreator;

import java.util.List;

/**
 * Repository to perform manipulations with News entities
 */
public interface NewsRepository extends CrudRepository<News> {

    List<News> search(NewsSearchQueryCreator queryCreator);
}
