package com.epam.lab.controller;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.TagDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.Set;

/**
 * <code>@Test</code>-annotated methods naming schema:
 * testedMethod_passedParamOrCondition_testedMethodShouldDoUnderCondition
 */
@RunWith(JUnit4.class)
public class InputDataValidationTest {

    private static final long POSITIVE_ID = 132;
    private static final String VALID_TEXT_FIELD = "valid";
    private static final Date VALID_DATE = null;
    private static final Date CURRENT_MOMENT_DATE = new Date();
    private static final long NEGATIVE_ID = -8;
    private static final String SIXTY_CHAR_TEXT_FIELD = "too_long_string_aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateAuthor_allFieldsValid_noViolations() {
        AuthorDto dtoToTest = validAuthor();
        Set<ConstraintViolation<AuthorDto>> violations = validator.validate(dtoToTest);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void validateAuthor_negativeId_oneViolation() {
        AuthorDto dtoToTest = validAuthor();
        dtoToTest.setId(NEGATIVE_ID);
        Set<ConstraintViolation<AuthorDto>> violations = validator.validate(dtoToTest);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void validateAuthor_negativeIdAndTooLongSurname_twoViolations() {
        AuthorDto dtoToTest = validAuthor();
        dtoToTest.setId(NEGATIVE_ID);
        dtoToTest.setSurname(SIXTY_CHAR_TEXT_FIELD);
        Set<ConstraintViolation<AuthorDto>> violations = validator.validate(dtoToTest);
        Assert.assertEquals(2, violations.size());
    }

    @Test
    public void validateAuthor_nullName_oneViolation() {
        AuthorDto dtoToTest = validAuthor();
        dtoToTest.setName(null);
        Set<ConstraintViolation<AuthorDto>> violations = validator.validate(dtoToTest);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void validateNews_allValid_noViolations() {
        NewsDto dtoToTest = validNews();
        Set<ConstraintViolation<NewsDto>> violations = validator.validate(dtoToTest);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void validateNews_creationDateIsNotNull_oneViolation() {
        NewsDto dtoToTest = validNews();
        dtoToTest.setCreationDate(CURRENT_MOMENT_DATE);
        Set<ConstraintViolation<NewsDto>> violations = validator.validate(dtoToTest);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void validateNews_modificationDateIsNotNull_oneViolation() {
        NewsDto dtoToTest = validNews();
        dtoToTest.setModificationDate(CURRENT_MOMENT_DATE);
        Set<ConstraintViolation<NewsDto>> violations = validator.validate(dtoToTest);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void validateNews_authorIsNotValid_oneViolation() {
        AuthorDto authorForNews = validAuthor();
        authorForNews.setSurname(null);
        NewsDto dtoToTest = validNews();
        dtoToTest.setAuthor(authorForNews);
        Set<ConstraintViolation<NewsDto>> violations = validator.validate(dtoToTest);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void validateTag_allValid_noViolations() {
        TagDto dtoToTest = validTag();
        Set<ConstraintViolation<TagDto>> violations = validator.validate(dtoToTest);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void validateTag_tooLongName_oneViolation() {
        TagDto dtoToTest = validTag();
        dtoToTest.setName(SIXTY_CHAR_TEXT_FIELD);
        Set<ConstraintViolation<TagDto>> violations = validator.validate(dtoToTest);
        Assert.assertEquals(1, violations.size());
    }

    private AuthorDto validAuthor() {
        return new AuthorDto.Builder()
                .id(POSITIVE_ID)
                .name(VALID_TEXT_FIELD)
                .surname(VALID_TEXT_FIELD)
                .build();
    }

    private NewsDto validNews() {
        return new NewsDto.Builder()
                .id(POSITIVE_ID)
                .title(VALID_TEXT_FIELD)
                .shortText(VALID_TEXT_FIELD)
                .fullText(VALID_TEXT_FIELD)
                .creationDate(VALID_DATE)
                .modificationDate(VALID_DATE)
                .author(validAuthor())
                .build();
    }

    private TagDto validTag() {
        return new TagDto.Builder()
                .id(POSITIVE_ID)
                .name(VALID_TEXT_FIELD)
                .build();
    }

}
