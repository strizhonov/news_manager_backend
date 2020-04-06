package com.epam.lab.dto;

import com.epam.lab.comparator.NewsSortType;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class to contain search criteria for news
 */
public class NewsSearchParamDto {

    // !important to persist LinedHashSet to impose order saving
    private LinkedHashSet<NewsSortType> sort = new LinkedHashSet<>();

    private Set<String> tags = new HashSet<>();

    @JsonProperty("author")
    private AuthorDto author;

    public NewsSearchParamDto() {
        // Empty bean constructor
    }

    public LinkedHashSet<NewsSortType> getSort() {
        return sort;
    }

    public void setSort(final LinkedHashSet<NewsSortType> sort) {
        this.sort = sort;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(final Set<String> tags) {
        this.tags = tags;
    }

    @Valid
    @Nullable
    public AuthorDto getAuthorDto() {
        return author;
    }

    public void setAuthorDto(final AuthorDto authorDto) {
        this.author = authorDto;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NewsSearchParamDto that = (NewsSearchParamDto) o;
        return Objects.equals(sort, that.sort) &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sort, tags, author);
    }

    @Override
    public String toString() {
        return "NewsSearchParamDto{" +
                "sortParams=" + sort +
                ", tagNames=" + tags +
                ", authorDto=" + author +
                '}';
    }

}
