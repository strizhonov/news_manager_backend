package com.epam.lab.service;

import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.util.EntityDtoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

/**
 * <code>@Test</code>-annotated methods naming schema:
 * testedMethod_passedParamOrCondition_testedMethodShouldDoUnderCondition
 */
@RunWith(MockitoJUnitRunner.class)
public class NewsServiceImplTest {

    private final static long ANY_POSITIVE_ID = 7;

    @Mock
    private NewsRepository repository;

    @Mock
    private EntityDtoMapper entityDtoMapper;

    @InjectMocks
    private NewsServiceImpl service;

    @Test
    public void getById_anyCase_callRepoGetByIdMethod() {
        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(validNews());

        service.getById(ANY_POSITIVE_ID);
        Mockito.verify(repository).getById(ANY_POSITIVE_ID);
    }

    @Test
    public void delete_anyCase_callRepoDeleteMethod() {
        service.delete(ANY_POSITIVE_ID);
        Mockito.verify(repository).delete(null);
    }

    @Test
    public void findAll_anyCase_callRepoFindAllMethod() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
        service.findAll();
        Mockito.verify(repository).findAll();
    }

    private News validNews() {
        Author validAuthor = new Author();
        validAuthor.setId(ANY_POSITIVE_ID);
        validAuthor.setName("author_name");
        validAuthor.setSurname("author_surname");

        News validNews = new News();
        validNews.setId(ANY_POSITIVE_ID);
        validNews.setTitle("news_title");
        validNews.setShortText("news_short_text");
        validNews.setFullText("news_full_text");
        validNews.setAuthor(validAuthor);
        return validNews;
    }

}
