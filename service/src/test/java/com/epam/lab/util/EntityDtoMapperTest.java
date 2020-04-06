package com.epam.lab.util;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.TagDto;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

/**
 * <code>@Test</code>-annotated methods naming schema:
 * testedMethod_passedParamOrCondition_testedMethodShouldDoUnderCondition
 */
@RunWith(JUnit4.class)
public class EntityDtoMapperTest {

    private static final int FIRST_GENERIC_ID = 1;
    private static final int SECOND_GENERIC_ID = 2;
    private static final int THIRD_GENERIC_ID = 3;
    private static final int FOURTH_GENERIC_ID = 4;
    private static final String TAG_ONE_NAME = "test_tag_one_name";
    private static final String TAG_TWO_NAME = "test_tag_two_name";
    private static final String AUTHOR_NAME = "test_author_name";
    private static final String AUTHOR_SURNAME = "test_author_surname";
    private static final String NEWS_TITLE = "test_news_title";
    private static final String NEWS_SHORT_TEXT = "test_news_short_text";
    private static final String NEWS_FULL_TEXT = "test_news_full_text";

    private final EntityDtoMapper entityDtoMapper = new EntityDtoMapper();

    @Test
    public void authorFromDto_anyValidAuthor_convertCorrectlyWithEqualFields() {
        Author entityToConvert = createGenericAuthor();

        AuthorDto convertedDto = entityDtoMapper.fromEntity(entityToConvert);

        Assert.assertEquals(entityToConvert.getId(), convertedDto.getId());
        Assert.assertEquals(entityToConvert.getName(), convertedDto.getName());
        Assert.assertEquals(entityToConvert.getSurname(), convertedDto.getSurname());
    }

    @Test
    public void authorFromEntity_anyValidAuthorDto_convertCorrectlyWithEqualFields() {
        AuthorDto dtoToConvert = new AuthorDto.Builder()
                .id(FIRST_GENERIC_ID)
                .name(AUTHOR_NAME)
                .surname(AUTHOR_SURNAME)
                .build();

        Author convertedEntity = entityDtoMapper.fromDto(dtoToConvert);

        Assert.assertEquals(convertedEntity.getId(), dtoToConvert.getId());
        Assert.assertEquals(convertedEntity.getName(), dtoToConvert.getName());
        Assert.assertEquals(convertedEntity.getSurname(), dtoToConvert.getSurname());
    }

    @Test
    public void newsFromEntity_anyValidNews_convertCorrectlyWithEqualFields() {
        News entityToConvert = createGenericNews();

        NewsDto convertedDto = entityDtoMapper.fromEntity(entityToConvert);

        Assert.assertEquals(entityToConvert.getId(), convertedDto.getId());
        Assert.assertEquals(entityToConvert.getTitle(), convertedDto.getTitle());
        Assert.assertEquals(entityToConvert.getShortText(), convertedDto.getShortText());
        Assert.assertEquals(entityToConvert.getFullText(), convertedDto.getFullText());
        Assert.assertEquals(entityToConvert.getCreationDate(), convertedDto.getCreationDate());
        Assert.assertEquals(entityToConvert.getModificationDate(), convertedDto.getModificationDate());
        Assert.assertEquals(entityToConvert.getAuthor().getId(), convertedDto.getAuthor().getId());
        Assert.assertEquals(entityToConvert.getAuthor().getName(), convertedDto.getAuthor().getName());
        Assert.assertEquals(entityToConvert.getAuthor().getSurname(), convertedDto.getAuthor().getSurname());
        Assert.assertEquals(((Tag) entityToConvert.getTags().toArray()[0]).getId(),
                ((TagDto) convertedDto.getTags().toArray()[0]).getId());
        Assert.assertEquals(((Tag) entityToConvert.getTags().toArray()[0]).getName(),
                ((TagDto) convertedDto.getTags().toArray()[0]).getName());
        Assert.assertEquals(((Tag) entityToConvert.getTags().toArray()[1]).getId(),
                ((TagDto) convertedDto.getTags().toArray()[1]).getId());
        Assert.assertEquals(((Tag) entityToConvert.getTags().toArray()[1]).getName(),
                ((TagDto) convertedDto.getTags().toArray()[1]).getName());
    }


    @Test
    public void newsFromDto_anyValidNewsDto_convertCorrectlyWithEqualFields() {
        NewsDto dtoToConvert = createGenericNewsDto();

        News convertedEntity = entityDtoMapper.fromDto(dtoToConvert);

        Assert.assertEquals(convertedEntity.getId(), dtoToConvert.getId());
        Assert.assertEquals(convertedEntity.getTitle(), dtoToConvert.getTitle());
        Assert.assertEquals(convertedEntity.getShortText(), dtoToConvert.getShortText());
        Assert.assertEquals(convertedEntity.getFullText(), dtoToConvert.getFullText());
        Assert.assertEquals(convertedEntity.getCreationDate(), dtoToConvert.getCreationDate());
        Assert.assertEquals(convertedEntity.getModificationDate(), dtoToConvert.getModificationDate());
        Assert.assertEquals(convertedEntity.getAuthor().getId(), dtoToConvert.getAuthor().getId());
        Assert.assertEquals(convertedEntity.getAuthor().getName(), dtoToConvert.getAuthor().getName());
        Assert.assertEquals(convertedEntity.getAuthor().getSurname(), dtoToConvert.getAuthor().getSurname());
        Assert.assertEquals(((Tag) convertedEntity.getTags().toArray()[0]).getId(),
                ((TagDto) dtoToConvert.getTags().toArray()[0]).getId());
        Assert.assertEquals(((Tag) convertedEntity.getTags().toArray()[0]).getName(),
                ((TagDto) dtoToConvert.getTags().toArray()[0]).getName());
        Assert.assertEquals(((Tag) convertedEntity.getTags().toArray()[1]).getId(),
                ((TagDto) dtoToConvert.getTags().toArray()[1]).getId());
        Assert.assertEquals(((Tag) convertedEntity.getTags().toArray()[1]).getName(),
                ((TagDto) dtoToConvert.getTags().toArray()[1]).getName());
    }

    @Test
    public void tagFromEntity_anyValidTag_convertCorrectlyWithEqualFields() {
        Tag tag = new Tag();
        tag.setId(FIRST_GENERIC_ID);
        tag.setName(TAG_ONE_NAME);

        TagDto tagDto = entityDtoMapper.fromEntity(tag);

        Assert.assertEquals(tag.getId(), tagDto.getId());
        Assert.assertEquals(tag.getName(), tagDto.getName());
    }

    @Test
    public void tagFromDto_anyValidTagDto_convertCorrectlyWithEqualFields() {
        TagDto tagDto = new TagDto.Builder()
                .id(FIRST_GENERIC_ID)
                .name(TAG_ONE_NAME)
                .build();

        Tag tag = entityDtoMapper.fromDto(tagDto);

        Assert.assertEquals(tag.getId(), tagDto.getId());
        Assert.assertEquals(tag.getName(), tagDto.getName());
    }

    @Test
    public void convertAllTags_twoTagsList_convertToTwoTagsDtoList() {
        Tag testTagOne = createGenericTag(1, TAG_ONE_NAME);
        Tag testTagTwo = createGenericTag(2, TAG_TWO_NAME);

        List<TagDto> converted = entityDtoMapper.convertAllTags(Arrays.asList(testTagOne, testTagTwo));
        Assert.assertEquals(2, converted.size());
        Assert.assertEquals(1, converted.get(0).getId());
        Assert.assertEquals(TAG_ONE_NAME, converted.get(0).getName());
        Assert.assertEquals(2, converted.get(1).getId());
        Assert.assertEquals(TAG_TWO_NAME, converted.get(1).getName());
    }


    @Test
    public void convertAllNews_oneNewsItemList_convertToOneNewsDtoList() {
        News testNewsOne = createGenericNews();
        List<NewsDto> converted = entityDtoMapper.convertAllNews(Collections.singletonList(testNewsOne));
        Assert.assertEquals(1, converted.size());
        Assert.assertEquals(NEWS_TITLE, converted.get(0).getTitle());
        Assert.assertEquals(NEWS_SHORT_TEXT, converted.get(0).getShortText());
        Assert.assertEquals(NEWS_FULL_TEXT, converted.get(0).getFullText());
        Assert.assertEquals(AUTHOR_NAME, converted.get(0).getAuthor().getName());
        Assert.assertEquals(2, converted.get(0).getTags().size());
    }


    @Test
    public void convertAllAuthors_oneAuthorList_convertToOneAuthorDtoList() {
        Author testAuthorOne = createGenericAuthor();
        List<AuthorDto> converted = entityDtoMapper.convertAllAuthors(Collections.singletonList(testAuthorOne));
        Assert.assertEquals(1, converted.size());
        Assert.assertEquals(AUTHOR_NAME, converted.get(0).getName());
        Assert.assertEquals(AUTHOR_SURNAME, converted.get(0).getSurname());
        Assert.assertEquals(SECOND_GENERIC_ID, converted.get(0).getId());
    }


    private Author createGenericAuthor() {
        Author genericAuthor = new Author();
        genericAuthor.setId(SECOND_GENERIC_ID);
        genericAuthor.setName(AUTHOR_NAME);
        genericAuthor.setSurname(AUTHOR_SURNAME);
        return genericAuthor;
    }

    private News createGenericNews() {
        News genericNews = new News();
        genericNews.setId(FIRST_GENERIC_ID);
        genericNews.setTitle(NEWS_TITLE);
        genericNews.setShortText(NEWS_SHORT_TEXT);
        genericNews.setFullText(NEWS_FULL_TEXT);
        genericNews.setCreationDate(new Date());
        genericNews.setModificationDate(new Date());

        Author authorForNews = createGenericAuthor();
        genericNews.setAuthor(authorForNews);

        Tag firstTagForNews = createGenericTag(THIRD_GENERIC_ID, TAG_ONE_NAME);
        Tag secondTagForNews = createGenericTag(FOURTH_GENERIC_ID, TAG_TWO_NAME);

        genericNews.setTags(new HashSet<>(Arrays.asList(firstTagForNews, secondTagForNews)));

        return genericNews;
    }

    private Tag createGenericTag(final int tagId, final String tagName) {
        Tag genericTag = new Tag();
        genericTag.setId(tagId);
        genericTag.setName(tagName);
        return genericTag;
    }


    private NewsDto createGenericNewsDto() {
        AuthorDto authorDtoForGenericNews = new AuthorDto.Builder()
                .id(FIRST_GENERIC_ID)
                .name(AUTHOR_NAME)
                .surname(AUTHOR_SURNAME)
                .build();
        TagDto firstTagDtoForNews = new TagDto.Builder()
                .id(SECOND_GENERIC_ID)
                .name(TAG_ONE_NAME)
                .build();
        TagDto secondTagDtoForNews = new TagDto.Builder()
                .id(THIRD_GENERIC_ID)
                .name(TAG_TWO_NAME)
                .build();

        return new NewsDto.Builder()
                .id(FOURTH_GENERIC_ID)
                .title(NEWS_TITLE)
                .shortText(NEWS_SHORT_TEXT)
                .fullText(NEWS_FULL_TEXT)
                .creationDate(new Date())
                .modificationDate(new Date())
                .author(authorDtoForGenericNews)
                .tags(new HashSet<>(Arrays.asList(firstTagDtoForNews, secondTagDtoForNews)))
                .build();
    }
}
