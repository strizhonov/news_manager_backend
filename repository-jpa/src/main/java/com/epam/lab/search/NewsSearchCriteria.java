package com.epam.lab.search;

import com.epam.lab.model.Author;
import org.springframework.lang.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class to contain search criteria for news
 */
public class NewsSearchCriteria {

    private Set<String> tagNames = new HashSet<>();

    @Nullable
    private Author author;

    public NewsSearchCriteria() {
        // Empty bean constructor
    }

    public Set<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(final Set<String> tagNames) {
        this.tagNames = tagNames;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(final Author author) {
        this.author = author;
    }

    public boolean hasTags() {
        return !tagNames.isEmpty();
    }

    public boolean hasAuthor() {
        return author != null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NewsSearchCriteria that = (NewsSearchCriteria) o;
        return Objects.equals(tagNames, that.tagNames) &&
                Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagNames, author);
    }

    @Override
    public String toString() {
        return "NewsSearchParam{" +
                ", tagNames=" + tagNames +
                ", author=" + author +
                '}';
    }

}
