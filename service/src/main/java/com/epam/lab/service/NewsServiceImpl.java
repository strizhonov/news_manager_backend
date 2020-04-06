package com.epam.lab.service;

import com.epam.lab.comparator.NewsComparatorFactoryImpl;
import com.epam.lab.comparator.NewsSortType;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.NewsSearchParamDto;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.search.NewsSearchCriteria;
import com.epam.lab.search.NewsSearchQueryCreator;
import com.epam.lab.util.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    private EntityDtoMapper mapper;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TagRepository tagRepository;

    /**
     * Create entity in the database, ignoring its id.
     * <p>
     * Set creation and modification date equals to creation moment
     * <p>
     * Create contained author and tags if they are new to the database.
     *
     * @param dtoToCreate to save in thr repository
     * @return dto that corresponds to saved entity
     */
    @Override
    public NewsDto create(final NewsDto dtoToCreate) {
        News newsToCreate = prepareForCreation(dtoToCreate);
        processContainedEntities(newsToCreate);
        News createdEntity = newsRepository.save(newsToCreate);
        return mapper.fromEntity(createdEntity);
    }


    @Override
    public NewsDto getById(final long dtoToGetId) {
        News entityGot = newsRepository.getById(dtoToGetId);
        return mapper.fromEntity(entityGot);
    }


    @Override
    public NewsDto update(final NewsDto dtoToUpdate) {
        News entityToUpdate = prepareForUpdating(dtoToUpdate);
        processContainedEntities(entityToUpdate);
        News updatedEntity = newsRepository.update(entityToUpdate);
        return mapper.fromEntity(updatedEntity);
    }


    @Override
    public void delete(final long dtoToDeleteId) {
        News entityToDelete = newsRepository.getById(dtoToDeleteId);
        newsRepository.delete(entityToDelete);
    }


    @Override
    public List<NewsDto> findAll() {
        return mapper.convertAllNews(newsRepository.findAll());
    }


    @Override
    public int count() {
        return findAll().size();
    }


    @Override
    public List<NewsDto> search(final NewsSearchParamDto searchParamsDto) {
        List<News> found = performSearching(searchParamsDto);
        performSorting(found, searchParamsDto);
        return mapper.convertAllNews(found);
    }


    private News prepareForCreation(final NewsDto dtoToCreate) {
        ignoreId(dtoToCreate);
        setDatesOnCreation(dtoToCreate);
        return mapper.fromDto(dtoToCreate);
    }


    private void ignoreId(final NewsDto dtoToCreate) {
        dtoToCreate.setId(0);
    }


    private void setDatesOnCreation(final NewsDto dtoToCreate) {
        Date now = new Date();
        dtoToCreate.setCreationDate(now);
        dtoToCreate.setModificationDate(now);
    }


    private void processContainedEntities(final News newsToProcess) {
        processNewsAuthor(newsToProcess);
        processNewsTags(newsToProcess);
    }


    private void processNewsAuthor(final News newsToProcess) {
        Author authorToCreate = newsToProcess.getAuthor();
        if (authorNotExists(authorToCreate)) {
            Author savedAuthor = authorRepository.save(authorToCreate);
            newsToProcess.setAuthor(savedAuthor);
        }
    }


    private void processNewsTags(final News newsToProcess) {
        for (Tag current : newsToProcess.getTags()) {
            processNewsTag(current, newsToProcess);
        }
    }


    private boolean authorNotExists(final Author authorToCheck) {
        return !authorRepository.findAll().contains(authorToCheck);
    }


    private void processNewsTag(final Tag tagToCreate, final News newsToProcess) {
        if (tagNotExists(tagToCreate)) {
            Tag savedTag = tagRepository.save(tagToCreate);
            newsToProcess.addTag(savedTag);
        }
    }


    private boolean tagNotExists(final Tag tagToCheck) {
        return !tagRepository.findAll().contains(tagToCheck);
    }


    private News prepareForUpdating(final NewsDto dtoToUpdate) {
        setDatesOnUpdating(dtoToUpdate);
        return mapper.fromDto(dtoToUpdate);
    }


    private void setDatesOnUpdating(final NewsDto dtoToUpdate) {
        Date now = new Date();
        dtoToUpdate.setModificationDate(now);
        News equivalentToDto = newsRepository.getById(dtoToUpdate.getId());
        dtoToUpdate.setCreationDate(equivalentToDto.getCreationDate());
    }


    private List<News> performSearching(final NewsSearchParamDto searchParamsDto) {
        NewsSearchCriteria searchCriteria = mapper.fromDto(searchParamsDto);
        return newsRepository.search(new NewsSearchQueryCreator(searchCriteria));
    }


    private void performSorting(final List<News> found, final NewsSearchParamDto searchParamsDto) {
        NewsSortType[] orderedSortTypes = searchParamsDto.getSort().toArray(new NewsSortType[0]);
        found.sort(new NewsComparatorFactoryImpl().get(orderedSortTypes));
    }

}
