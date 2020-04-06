package com.epam.lab.service;

import com.epam.lab.dto.TagDto;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private EntityDtoMapper mapper;

    @Autowired
    private TagRepository repository;

    /**
     * Create entity in the database, ignoring its id.
     *
     * @param dtoToCreate to save in thr repository
     * @return dto that corresponds to saved entity
     */
    @Override
    public TagDto create(final TagDto dtoToCreate) {
        ignoreId(dtoToCreate);
        Tag entityToCreate = mapper.fromDto(dtoToCreate);
        Tag createdEntity = repository.save(entityToCreate);
        return mapper.fromEntity(createdEntity);
    }


    @Override
    public TagDto getById(final long dtoToGetId) {
        Tag retrievedEntity = repository.getById(dtoToGetId);
        return mapper.fromEntity(retrievedEntity);
    }


    @Override
    public TagDto update(final TagDto dtoToUpdate) {
        Tag entityToUpdate = mapper.fromDto(dtoToUpdate);
        Tag updatedEntity = repository.update(entityToUpdate);
        return mapper.fromEntity(updatedEntity);
    }


    @Override
    public void delete(final long dtoToDeleteId) {
        Tag entityToDelete = repository.getById(dtoToDeleteId);
        repository.delete(entityToDelete);
    }


    @Override
    public List<TagDto> findAll() {
        List<Tag> allTags = repository.findAll();
        return mapper.convertAllTags(allTags);
    }


    private void ignoreId(final TagDto dtoToCreate) {
        dtoToCreate.setId(0);
    }

}
