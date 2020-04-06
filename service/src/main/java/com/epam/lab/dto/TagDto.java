package com.epam.lab.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TagDto {

    private long id;

    private String name;

    public TagDto() {
        // Empty bean constructor
    }

    private TagDto(final Builder builder) {
        setId(builder.id);
        setName(builder.name);
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
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TagDto tagDto = (TagDto) o;
        return id == tagDto.id &&
                Objects.equals(name, tagDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


    public static final class Builder {
        private long id;
        private String name;

        public Builder() {
            // Builder empty constructor
        }

        public Builder id(final long val) {
            id = val;
            return this;
        }

        public Builder name(final String val) {
            name = val;
            return this;
        }

        public TagDto build() {
            return new TagDto(this);
        }
    }
}
