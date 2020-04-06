package com.epam.lab.comparator;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum NewsSortType {

    @JsonProperty("author")
    AUTHOR_SURNAME,

    @JsonProperty("tags")
    NUMBER_OF_TAGS,

    @JsonProperty("date")
    CREATION_DATE;

}