package serviceit;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.service.AuthorService;
import com.epam.lab.service.NewsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * <code>@Test</code>-annotated methods naming schema:
 * testedMethod_passedParamOrCondition_testedMethodShouldDoUnderCondition
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITServiceConfig.class, loader = AnnotationConfigContextLoader.class)
public class AuthorServiceIT {

    private static final int ID_TO_DELETE = 6;
    private static final int NUMBER_OF_NEWS_OF_DELETED_AUTHOR = 3;
    private static final int ID_TO_PERSIST = 5;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private NewsService newsService;

    @Test
    public void create_existingByNameAndSurnameAuthor_notSaveEntity() {
        int authorsTotalNumberBefore = authorService.findAll().size();

        AuthorDto dtoToTest = authorService.getById(ID_TO_PERSIST);
        authorService.create(dtoToTest);

        int authorsTotalNumberAfter = authorService.findAll().size();

        Assert.assertEquals(authorsTotalNumberBefore, authorsTotalNumberAfter);
    }

    @Test
    public void create_nonExistingByNameAndSurnameAuthor_saveEntity() {
        AuthorDto dtoToTest = new AuthorDto();
        dtoToTest.setName("test_name_from_create");
        dtoToTest.setSurname("test_surname_from_create");

        int authorsTotalNumberBefore = authorService.findAll().size();

        authorService.create(dtoToTest);

        int authorsTotalNumberAfter = authorService.findAll().size();

        Assert.assertEquals(authorsTotalNumberBefore + 1, authorsTotalNumberAfter);
    }

    @Test
    public void getById_idPresentsInDatabase_getEntity() {
        AuthorDto dtoToTest = authorService.getById(ID_TO_PERSIST);

        Assert.assertNotNull(dtoToTest);
    }

    @Test(expected = RuntimeException.class)
    public void getById_idNotPresentsInDatabase_throwException() {
        authorService.getById(0);
    }

    @Test
    public void update_existingByIdAuthor_updateEntity() {
        AuthorDto dtoToTest = authorService.getById(ID_TO_PERSIST);
        String newNameToSet = "updated_name";
        dtoToTest.setName(newNameToSet);

        authorService.update(dtoToTest);
        String updatedNameFromDB = authorService.getById(dtoToTest.getId()).getName();

        Assert.assertEquals(newNameToSet, updatedNameFromDB);
    }

    @Test(expected = RuntimeException.class)
    public void update_nonExistingByIdAuthor_throwException() {
        AuthorDto dtoToTest = new AuthorDto();
        dtoToTest.setName("test_name_from_update");

        authorService.update(dtoToTest);
    }

    @Test
    public void delete_idPresentsInDatabase_removeAuthor() {
        int authorsTotalNumberBefore = authorService.findAll().size();
        int newsTotalNumberBefore = newsService.findAll().size();

        authorService.delete(ID_TO_DELETE);

        int authorsTotalNumberAfter = authorService.findAll().size();
        int newsTotalNumberAfter = newsService.findAll().size();

        Assert.assertEquals(authorsTotalNumberBefore - 1, authorsTotalNumberAfter);
        Assert.assertEquals(newsTotalNumberBefore - NUMBER_OF_NEWS_OF_DELETED_AUTHOR, newsTotalNumberAfter);
    }

    @Test(expected = RuntimeException.class)
    public void delete_idNotPresentsInDatabase_throwException() {
        authorService.delete(0);
    }

}
