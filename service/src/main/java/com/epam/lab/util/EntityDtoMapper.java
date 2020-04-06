package com.epam.lab.util;

import com.epam.lab.dto.*;
import com.epam.lab.model.*;
import com.epam.lab.search.NewsSearchCriteria;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class EntityDtoMapper {

    public Author fromDto(final AuthorDto dtoFrom) {
        Author entityTo = new Author();
        BeanUtils.copyProperties(dtoFrom, entityTo);
        return entityTo;
    }

    public AuthorDto fromEntity(final Author entityFrom) {
        AuthorDto dtoTo = new AuthorDto();
        BeanUtils.copyProperties(entityFrom, dtoTo);
        return dtoTo;
    }

    public News fromDto(final NewsDto dtoFrom) {
        News entityTo = new News();
        BeanUtils.copyProperties(dtoFrom, entityTo);
        entityTo.setAuthor(fromDto(dtoFrom.getAuthor()));
        entityTo.setTags(convertAllTagDtos(dtoFrom.getTags()));
        return entityTo;
    }

    public NewsDto fromEntity(final News entityFrom) {
        NewsDto dtoTo = new NewsDto();
        BeanUtils.copyProperties(entityFrom, dtoTo);
        dtoTo.setAuthor(fromEntity(entityFrom.getAuthor()));
        dtoTo.setTags(convertAllTags(entityFrom.getTags()));
        return dtoTo;
    }

    public Tag fromDto(final TagDto dtoFrom) {
        Tag entityTo = new Tag();
        BeanUtils.copyProperties(dtoFrom, entityTo);
        return entityTo;
    }

    public TagDto fromEntity(final Tag entityFrom) {
        TagDto dtoTo = new TagDto();
        BeanUtils.copyProperties(entityFrom, dtoTo);
        return dtoTo;
    }

    public User fromDto(final UserDto dtoFrom) {
        User entityTo = new User();
        BeanUtils.copyProperties(dtoFrom, entityTo);
        return entityTo;
    }

    public UserDto fromEntity(final User entityFrom) {
        UserDto dtoTo = new UserDto();
        BeanUtils.copyProperties(entityFrom, dtoTo);
        return dtoTo;
    }

    public Client fromDto(final ClientDto dtoFrom) {
        Client entityTo = new Client();
        BeanUtils.copyProperties(dtoFrom, entityTo);
        entityTo.setAuthorizedGrantType(dtoFrom.getGrantType().getValue());
        return entityTo;
    }

    public ClientDto fromEntity(final Client entityFrom) {
        ClientDto dtoTo = new ClientDto();
        BeanUtils.copyProperties(entityFrom, dtoTo);
        String grantType = entityFrom.getAuthorizedGrantType();
        dtoTo.setGrantType(ClientDto.GrantType.fromString(grantType));
        return dtoTo;
    }

    public Set<TagDto> convertAllTags(final Set<Tag> entitiesSetFrom) {
        Set<TagDto> dtosSetTo = new HashSet<>();
        for (Tag tag : entitiesSetFrom) {
            dtosSetTo.add(fromEntity(tag));
        }
        return dtosSetTo;
    }

    public List<TagDto> convertAllTags(final List<Tag> entitiesListFrom) {
        List<TagDto> dtos = new ArrayList<>();
        for (Tag tag : entitiesListFrom) {
            dtos.add(fromEntity(tag));
        }
        return dtos;
    }

    public List<AuthorDto> convertAllAuthors(final List<Author> entitiesListFrom) {
        List<AuthorDto> dtoListTo = new ArrayList<>();
        for (Author author : entitiesListFrom) {
            dtoListTo.add(fromEntity(author));
        }
        return dtoListTo;
    }

    public List<NewsDto> convertAllNews(final List<News> entitiesListFrom) {
        List<NewsDto> dtoListTo = new ArrayList<>();

        for (News news : entitiesListFrom) {
            NewsDto currentDto = fromEntity(news);

            AuthorDto authorDto = fromEntity(news.getAuthor());
            currentDto.setAuthor(authorDto);

            Set<TagDto> tagDtos = convertAllTags(news.getTags());
            currentDto.setTags(tagDtos);

            dtoListTo.add(currentDto);
        }

        return dtoListTo;
    }

    public Set<Tag> convertAllTagDtos(final Set<TagDto> dtosSetFrom) {
        Set<Tag> entitiesSetTo = new HashSet<>();
        for (TagDto dto : dtosSetFrom) {
            entitiesSetTo.add(fromDto(dto));
        }
        return entitiesSetTo;
    }

    public NewsSearchCriteria fromDto(final NewsSearchParamDto dtoToConvert) {
        NewsSearchCriteria result = new NewsSearchCriteria();

        AuthorDto authorToSearch = dtoToConvert.getAuthorDto();
        if (authorToSearch != null) {
            Author convertedAuthor = fromDto(authorToSearch);
            result.setAuthor(convertedAuthor);
        }
        result.setTagNames(dtoToConvert.getTags());

        return result;
    }

}
