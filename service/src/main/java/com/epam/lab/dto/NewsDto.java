package com.epam.lab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class NewsDto {

    private long id;

    private String title;

    private String shortText;

    private String fullText;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date modificationDate;

    private AuthorDto author;

    private Set<TagDto> tags = new HashSet<>();

    public NewsDto() {
        // Empty bean constructor
    }

    private NewsDto(final Builder builder) {
        setId(builder.id);
        setTitle(builder.title);
        setShortText(builder.shortText);
        setFullText(builder.fullText);
        setCreationDate(builder.creationDate);
        setModificationDate(builder.modificationDate);
        setAuthor(builder.author);
        setTags(builder.tags);
    }

    public void addTagDto(final TagDto tagToAdd) {
        tags.add(tagToAdd);
    }

    @Min(0)
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    @NotNull
    @Size(max = 30)
    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @NotNull
    @Size(max = 100)
    public String getShortText() {
        return shortText;
    }

    public void setShortText(final String shortText) {
        this.shortText = shortText;
    }

    @NotNull
    @Size(max = 2000)
    public String getFullText() {
        return fullText;
    }

    public void setFullText(final String fullText) {
        this.fullText = fullText;
    }

    @Null
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

    @Null
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

    @NotNull
    @Valid
    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(final AuthorDto author) {
        this.author = author;
    }

    public Set<TagDto> getTags() {
        return tags;
    }

    public void setTags(final Set<TagDto> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NewsDto newsDto = (NewsDto) o;
        return id == newsDto.id &&
                Objects.equals(title, newsDto.title) &&
                Objects.equals(shortText, newsDto.shortText) &&
                Objects.equals(fullText, newsDto.fullText) &&
                Objects.equals(creationDate, newsDto.creationDate) &&
                Objects.equals(modificationDate, newsDto.modificationDate) &&
                Objects.equals(author, newsDto.author) &&
                Objects.equals(tags, newsDto.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, shortText, fullText, creationDate, modificationDate, author, tags);
    }

    @Override
    public String toString() {
        return "NewsDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", shortText='" + shortText + '\'' +
                ", fullText='" + fullText + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", author=" + author +
                ", tags=" + tags +
                '}';
    }


    public static final class Builder {
        private long id;
        private String title;
        private String shortText;
        private String fullText;
        private Date creationDate;
        private Date modificationDate;
        private AuthorDto author;
        private Set<TagDto> tags = new HashSet<>();

        public Builder() {
            // Builder empty constructor
        }

        public Builder id(final long val) {
            id = val;
            return this;
        }

        public Builder title(final String val) {
            title = val;
            return this;
        }

        public Builder shortText(final String val) {
            shortText = val;
            return this;
        }

        public Builder fullText(final String val) {
            fullText = val;
            return this;
        }

        public Builder creationDate(final Date val) {
            if (val == null) {
                this.creationDate = null;
            } else {
                this.creationDate = new Date(val.getTime());
            }
            return this;
        }

        public Builder modificationDate(final Date val) {
            if (val == null) {
                this.modificationDate = null;
            } else {
                this.modificationDate = new Date(val.getTime());
            }
            return this;
        }

        public Builder author(final AuthorDto val) {
            author = val;
            return this;
        }

        public Builder tags(final Set<TagDto> val) {
            tags = val;
            return this;
        }

        public NewsDto build() {
            return new NewsDto(this);
        }
    }
}
