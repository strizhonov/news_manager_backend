package com.epam.lab.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class AuthorDto {

    private long id;

    private String name;

    private String surname;

    public AuthorDto() {
        // Empty bean constructor
    }

    private AuthorDto(final Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setSurname(builder.surname);
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

    @NotNull
    @Size(max = 30)
    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AuthorDto authorDto = (AuthorDto) o;
        return id == authorDto.id &&
                Objects.equals(name, authorDto.name) &&
                Objects.equals(surname, authorDto.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }


    public static final class Builder {
        private long id;
        private String name;
        private String surname;

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

        public Builder surname(final String val) {
            surname = val;
            return this;
        }

        public AuthorDto build() {
            return new AuthorDto(this);
        }
    }
}
