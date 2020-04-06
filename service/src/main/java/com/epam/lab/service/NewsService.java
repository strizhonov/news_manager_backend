package com.epam.lab.service;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.NewsSearchParamDto;

import java.util.List;

/**
 * Interface for specific operations with Authors
 */
public interface NewsService extends AppService<NewsDto> {


    /**
     * Count the number of News saved in repository
     *
     * @return the number of News saved in repository
     */
    int count();


    /**
     * Retrieve list of news, filtered and sorted by passed args.
     *
     * @param searchParamsDto - data for result filtering and sorting
     * @return list of news, filtered and sorted by passed args.
     */
    List<NewsDto> search(NewsSearchParamDto searchParamsDto);
}
