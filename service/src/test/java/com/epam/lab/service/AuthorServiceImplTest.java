package com.epam.lab.service;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.util.EntityDtoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

/**
 * <code>@Test</code>-annotated methods naming schema:
 * testedMethod_passedParamOrCondition_testedMethodShouldDoUnderCondition
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceImplTest {

    private final static long ANY_POSITIVE_ID = 7;

    @Mock
    private AuthorRepository repository;

    @Mock
    private EntityDtoMapper entityDtoMapper;

    @InjectMocks
    private AuthorServiceImpl service;

    @Test
    public void create_anyValidAuthor_callRepoSaveMethod() {
        Mockito.when(repository.save(Mockito.any(Author.class))).thenReturn(validAuthor());
        Mockito.when(entityDtoMapper.fromDto(Mockito.any(AuthorDto.class))).thenReturn(validAuthor());

        service.create(validAuthorDto());
        Mockito.verify(repository).save(validAuthor());
    }

    @Test
    public void getById_anyCase_callRepoGetByIdMethod() {
        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(validAuthor());

        service.getById(ANY_POSITIVE_ID);
        Mockito.verify(repository).getById(ANY_POSITIVE_ID);
    }

    @Test
    public void update_anyValidAuthor_callRepoUpdateMethod() {
        Mockito.when(repository.update(Mockito.any(Author.class))).thenReturn(validAuthor());
        Mockito.when(entityDtoMapper.fromDto(Mockito.any(AuthorDto.class))).thenReturn(validAuthor());

        service.update(validAuthorDto());
        Mockito.verify(repository).update(validAuthor());
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

    private AuthorDto validAuthorDto() {
        return new AuthorDto.Builder()
                .id(ANY_POSITIVE_ID)
                .name("valid_author_name")
                .surname("valid_author_surname")
                .build();
    }

    private Author validAuthor() {
        Author validAuthor = new Author();
        validAuthor.setId(ANY_POSITIVE_ID);
        validAuthor.setName("valid_author_name");
        validAuthor.setSurname("valid_author_surname");
        return validAuthor;
    }

}
