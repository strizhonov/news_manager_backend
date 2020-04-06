package com.epam.lab.search;

import com.epam.lab.model.*;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Class to implement query building from NewsSearchCriteria
 */
public class NewsSearchQueryCreator {

    private final NewsSearchCriteria searchParams;
    private CriteriaQuery<News> masterQuery;
    private Root<News> newsRoot;
    private CriteriaBuilder jpaCriteriaBuilder;


    public NewsSearchQueryCreator(final NewsSearchCriteria searchParams) {
        this.searchParams = searchParams;
    }


    public CriteriaQuery<News> createJpaQuery(final CriteriaBuilder jpaCriteriaBuilder) {
        init(jpaCriteriaBuilder);
        enableWhere();
        return selectDistinct();
    }


    private void init(final CriteriaBuilder jpaCriteriaBuilder) {
        this.jpaCriteriaBuilder = jpaCriteriaBuilder;
        masterQuery = jpaCriteriaBuilder.createQuery(News.class);
        newsRoot = createRoot();
    }


    private Root<News> createRoot() {
        return masterQuery.from(News.class);
    }


    private void enableWhere() {
        List<Predicate> allPredicates = new ArrayList<>();

        if (searchParams.hasTags()) {
            addTagsPredicates(allPredicates);
        }

        if (searchParams.hasAuthor()) {
            addAuthorPredicates(allPredicates);
        }

        if (!allPredicates.isEmpty()) {
            Predicate tagsAndAuthorPredicate = jpaCriteriaBuilder.and(allPredicates.toArray(new Predicate[0]));
            masterQuery.where(tagsAndAuthorPredicate);
        }
    }

    private void addTagsPredicates(List<Predicate> allPredicates) {
        List<Predicate> tagsPredicates = createTagsPredicates();
        Predicate tagsOrPredicate = jpaCriteriaBuilder.or(tagsPredicates.toArray(new Predicate[0]));
        allPredicates.add(tagsOrPredicate);
    }

    private void addAuthorPredicates(List<Predicate> allPredicates) {
        List<Predicate> authorPredicates = createAuthorPredicates();
        Predicate authorAndPredicate = jpaCriteriaBuilder.and(authorPredicates.toArray(new Predicate[0]));
        allPredicates.add(authorAndPredicate);

    }

    private List<Predicate> createTagsPredicates() {
        List<Predicate> tagsPredicates = new ArrayList<>();
        Set<String> tagNames = searchParams.getTagNames();

        Join<News, Tag> newsTag = newsRoot.join(News_.tags, JoinType.INNER);
        for (String tagName : tagNames) {
            tagsPredicates.add(jpaCriteriaBuilder.equal(newsTag.get(Tag_.NAME), tagName));
        }

        return tagsPredicates;
    }

    private List<Predicate> createAuthorPredicates() {
        List<Predicate> authorPredicates = new ArrayList<>();

        Author author = searchParams.getAuthor();

        Join<News, Author> newsAuthor = newsRoot.join(News_.author, JoinType.INNER);
        authorPredicates.add(
                jpaCriteriaBuilder.equal(newsAuthor.get(Author_.NAME), Objects.requireNonNull(author).getName()));
        authorPredicates.add(
                jpaCriteriaBuilder.equal(newsAuthor.get(Author_.SURNAME), author.getSurname()));

        return authorPredicates;
    }

    private CriteriaQuery<News> selectDistinct() {
        return masterQuery.select(newsRoot).distinct(true);
    }

}
