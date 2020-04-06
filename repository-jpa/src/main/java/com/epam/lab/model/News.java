package com.epam.lab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "news")
public class News implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "short_text")
    private String shortText;

    @Column(name = "full_text")
    private String fullText;

    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "modification_date")
    @Temporal(TemporalType.DATE)
    private Date modificationDate;

    @ManyToOne
    @JoinTable(
            name = "news_author",
            joinColumns = @JoinColumn(name = "news_id")
    )
    private Author author;

    @ManyToMany(targetEntity = Tag.class)
    @JoinTable(
            name = "news_tag",
            joinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags = new HashSet<>();

    public News() {
        // Empty bean constructor
    }

    public void addTag(final Tag tagToAdd) {
        tags.add(tagToAdd);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(final String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(final String fullText) {
        this.fullText = fullText;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(final Author author) {
        this.author = author;
    }

    public Set<Tag> getTags() {
        return new HashSet<>(tags);
    }

    public void setTags(final Set<Tag> tags) {
        this.tags = new HashSet<>(tags);
    }

    public Date getCreationDate() {
        if (creationDate == null) {
            return null;
        } else {
            return new Date(creationDate.getTime());
        }
    }

    public void setCreationDate(final Date creationDate) {
        if (creationDate == null) {
            this.creationDate = null;
        } else {
            this.creationDate = new Date(creationDate.getTime());
        }
    }

    public Date getModificationDate() {
        if (modificationDate == null) {
            return null;
        } else {
            return new Date(modificationDate.getTime());
        }
    }

    public void setModificationDate(final Date modificationDate) {
        if (modificationDate == null) {
            this.modificationDate = null;
        } else {
            this.modificationDate = new Date(modificationDate.getTime());
        }
    }

    /**
     * Compare current object to passed object by all fields, EXCEPT ID
     *
     * @param o object to compare with current
     * @return true if objects are equal, and false if not
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final News news = (News) o;
        return Objects.equals(title, news.title) &&
                Objects.equals(shortText, news.shortText) &&
                Objects.equals(fullText, news.fullText) &&
                Objects.equals(creationDate, news.creationDate) &&
                Objects.equals(modificationDate, news.modificationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, shortText, fullText, creationDate, modificationDate);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", shortText='" + shortText + '\'' +
                ", fullText='" + fullText + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }


}
