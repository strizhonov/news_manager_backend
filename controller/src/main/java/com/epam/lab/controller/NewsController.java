package com.epam.lab.controller;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.NewsSearchParamDto;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService service;

    /**
     * Save News into the database.
     * <p>
     * A News id value will not be considered during the operation.
     * <p>
     * If the same entity does already present, there is no changes performed.
     * <p>
     * If News contains Tag and/or Author
     * that does not exist, it will be created either.
     * <p>
     * If News is successfully added or the same entity does already present,
     * user gets response code "200". Otherwise user gets a code number that
     * corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToSave to insert into the database
     * @return created News
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public NewsDto save(@RequestBody @Valid final NewsDto dtoToSave) {
        return service.create(dtoToSave);
    }


    /**
     * Get News from the database by its id field.
     * <p>
     * If News is successfully retrieved, user gets response code "200".
     * Otherwise user gets a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToGetId of the entity to be found
     * @return found news item
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public NewsDto get(@PathVariable("id") @Positive(message = "Illegal id, should be greater then 0") final long dtoToGetId) {
        return service.getById(dtoToGetId);
    }


    /**
     * Update News in the database.
     * <p>
     * A News id value will be considered during the operation.
     * <p>
     * If News contains Tag and/or Author that does not exist, it will be created.
     * <p>
     * If News is successfully updated, user gets response code "200". Otherwise user gets
     * a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToUpdate to update
     * @return updated news item
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public NewsDto update(@RequestBody @Valid final NewsDto dtoToUpdate) {
        return service.update(dtoToUpdate);
    }


    /**
     * Delete News from the database by its id field.
     * <p>
     * If News is successfully deleted, user gets response code "200".
     * Otherwise user gets a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param dtoToDeleteId id of the entity to delete
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @Positive(message = "Illegal id, should be greater then 0") final long dtoToDeleteId) {
        service.delete(dtoToDeleteId);
    }


    /**
     * Retrieve all News from database
     * <p>
     * If List of News is successfully retrieved, user gets response code "200".
     * Otherwise user gets a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @return dto list that corresponds to the found entity
     * @see com.epam.lab.exception.AppExceptionHandler
     */
    @PreAuthorize("permitAll()")
    @GetMapping
    public List<NewsDto> findAll() {
        return service.findAll();
    }


    /**
     * Search News by <code>NewsSearchParamDto</code> and get List of all found News.
     * <p>
     * <code>NewsSearchParamDto</code> object consist of valid author, set of tags and set of sort params.
     * <p>
     * Author's id will not be considered for search and can be skipped.
     * <p>
     * Search schema: [ (tag1_name OR tag2_name OR ... OR tagN_name) AND author_name AND author_surname ].
     * <p>
     * Author can be null and tags can be empty, then every present news item will be retrieved.
     * <p>
     * If there is sort param in the request, result will be sorted according to its value.
     * Possible param values:
     * <p>
     * - date - result List will be sorted by the News' creationDate ascending;
     * <p>
     * - tags - result List will be sorted by the News' number of tags ascending;
     * <p>
     * - author - result List will be sorted by the News' Author surname ascending.
     * <p>
     * Sort params will be considered in the mentioned order.
     * If there's no sort param passed, there's no sorting performed.
     * <p>
     * If List of News is successfully retrieved, user gets response code "200".
     * Otherwise user gets a code number that corresponds to <code>AppExceptionHandler</code> ruling.
     *
     * @param searchParamsDto - News with searched according to this data will be retrieved.
     * @return dto list that corresponds to the found entities
     * @see com.epam.lab.exception.AppExceptionHandler
     * @see com.epam.lab.dto.NewsSearchParamDto
     */
    @PreAuthorize("permitAll()")
    @PostMapping("/search")
    public List<NewsDto> search(@RequestBody(required = false) @Nullable final NewsSearchParamDto searchParamsDto) {
        if (criteriaAreAbsent(searchParamsDto)) {
            return findAll();
        } else {
            return service.search(searchParamsDto);
        }
    }


    /**
     * Count existing news
     *
     * @return number of saved news
     */
    @PreAuthorize("permitAll()")
    @GetMapping("/count")
    public int count() {
        return service.count();
    }


    private boolean criteriaAreAbsent(final NewsSearchParamDto searchParamsDto) {
        if (searchParamsDto == null) {
            return true;
        } else {
            return searchParamsDto.getAuthorDto() == null
                    && searchParamsDto.getSort().isEmpty()
                    && searchParamsDto.getTags().isEmpty();
        }
    }

}
