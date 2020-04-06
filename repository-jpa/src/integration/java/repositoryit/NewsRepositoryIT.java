package repositoryit;

import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.NewsRepositoryImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * <code>@Test</code>-annotated methods naming schema:
 * testedMethod_passedParamOrCondition_testedMethodShouldDoUnderCondition
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITRepositoryConfig.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class NewsRepositoryIT {

    private static final int NEWS_TO_DELETE_ID = 1;
    private static final int EXISTING_NEWS_ID = 2;

    @Autowired
    private NewsRepositoryImpl repository;

    private Author anyValidAuthor;
    private Tag anyValidTagOne;
    private Tag anyValidTagTwo;

    @After
    public void processTransaction() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }

    @Test
    public void save_newsItemWithSuchIdPresents_notSaveEntity() {
        News entityToTest = repository.getById(EXISTING_NEWS_ID);

        int newsTotalNumberBefore = repository.findAll().size();
        repository.save(entityToTest);
        int newsTotalNumberAfter = repository.findAll().size();

        Assert.assertEquals(newsTotalNumberBefore, newsTotalNumberAfter);
    }

    @Test
    public void getById_newsItemWithSuchIdPresents_getEntity() {
        News entityToTest = repository.getById(EXISTING_NEWS_ID);

        Assert.assertNotNull(entityToTest);
    }

    @Test(expected = RuntimeException.class)
    public void getById_newsItemWithSuchIdNotPresents_throwException() {
        repository.getById(0);
    }

    @Test
    public void update_newsItemWithSuchIdPresents_updateEntity() {
        News entityToTest = repository.getById(EXISTING_NEWS_ID);
        String updatedTitle = "updated_title";
        entityToTest.setTitle(updatedTitle);

        repository.update(entityToTest);

        News updated = repository.getById(EXISTING_NEWS_ID);

        Assert.assertEquals(updatedTitle, updated.getTitle());
    }

    @Test(expected = RuntimeException.class)
    public void update_newsItemWithSuchIdNotPresents_throwException() {
        repository.update(new News());
    }

    @Test
    public void delete_newsItemWithSuchIdPresents_removeEntity() {
        int newsTotalNumberBefore = repository.findAll().size();

        News entityToDelete = repository.getById(NEWS_TO_DELETE_ID);
        repository.delete(entityToDelete);
        int newsTotalNumberAfter = repository.findAll().size();

        Assert.assertEquals(newsTotalNumberBefore - 1, newsTotalNumberAfter);
    }

    @Before
    public void initEntities() {
        anyValidAuthor = new Author();
        anyValidAuthor.setName("Any");
        anyValidAuthor.setSurname("Valid");

        anyValidTagOne = new Tag();
        anyValidTagOne.setName("valid_tag_one");

        anyValidTagTwo = new Tag();
        anyValidTagTwo.setName("valid_tag_two");
    }

}
