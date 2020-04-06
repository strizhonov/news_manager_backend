package com.epam.lab.service;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private EntityDtoMapper mapper;

    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Create entity in the database, ignoring its id.
     *
     * @param dtoToCreate to save in thr repository
     * @return dto that corresponds to saved entity
     */
    @Override
    public AuthorDto create(final AuthorDto dtoToCreate) {
        ignoreId(dtoToCreate);
        Author entityToCreate = mapper.fromDto(dtoToCreate);
        Author createdEntity = authorRepository.save(entityToCreate);
        return mapper.fromEntity(createdEntity);
    }


    @Override
    public AuthorDto getById(final long dtoToGetId) {
        Author retrievedEntity = authorRepository.getById(dtoToGetId);
        return mapper.fromEntity(retrievedEntity);
    }


    @Override
    public AuthorDto update(final AuthorDto dtoToUpdate) {
        Author entityToUpdate = mapper.fromDto(dtoToUpdate);
        Author updatedEntity = authorRepository.update(entityToUpdate);
        return mapper.fromEntity(updatedEntity);
    }


    @Override
    public void delete(final long dtoToDeleteId) {
        Author entityToDelete = authorRepository.getById(dtoToDeleteId);
        authorRepository.delete(entityToDelete);
    }


    @Override
    public List<AuthorDto> findAll() {
        List<Author> allAuthors = authorRepository.findAll();
        return mapper.convertAllAuthors(allAuthors);
    }


    private void ignoreId(final AuthorDto dtoToCreate) {
        dtoToCreate.setId(0);
    }

}
