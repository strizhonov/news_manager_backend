package com.epam.lab.service;

import com.epam.lab.dto.TagDto;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
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
public class TagServiceImplTest {

    private final static long ANY_POSITIVE_ID = 5;

    @Mock
    private TagRepository repository;

    @Mock
    private EntityDtoMapper entityDtoMapper;

    @InjectMocks
    private TagServiceImpl service;

    @Test
    public void create_anyValidTag_callRepoSaveMethod() {
        Mockito.when(repository.save(Mockito.any(Tag.class))).thenReturn(validTag());
        Mockito.when(entityDtoMapper.fromDto(Mockito.any(TagDto.class))).thenReturn(validTag());

        service.create(validTagDto());
        Mockito.verify(repository).save(validTag());
    }

    @Test
    public void getById_anyCase_callRepoGetByIdMethod() {
        Mockito.when(repository.getById(Mockito.anyLong())).thenReturn(validTag());
        service.getById(ANY_POSITIVE_ID);
        Mockito.verify(repository).getById(ANY_POSITIVE_ID);
    }

    @Test
    public void update_anyValidTag_callRepoUpdateMethod() {
        Mockito.when(repository.update(Mockito.any(Tag.class))).thenReturn(validTag());
        Mockito.when(entityDtoMapper.fromDto(Mockito.any(TagDto.class))).thenReturn(validTag());

        service.update(validTagDto());
        Mockito.verify(repository).update(validTag());
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

    private TagDto validTagDto() {
        return new TagDto.Builder()
                .id(ANY_POSITIVE_ID)
                .name("valid_tag_name")
                .build();
    }

    private Tag validTag() {
        Tag validTag = new Tag();
        validTag.setId(ANY_POSITIVE_ID);
        validTag.setName("valid_tag_name");
        return validTag;
    }

}
