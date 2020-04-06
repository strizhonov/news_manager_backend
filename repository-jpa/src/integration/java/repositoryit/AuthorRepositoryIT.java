package repositoryit;

import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepositoryImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
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
public class AuthorRepositoryIT extends AbstractTransactionalJUnit4SpringContextTests {

    private static final int ID_TO_PERSIST = 5;

    @Autowired
    private AuthorRepositoryImpl repository;

    @After
    public void processTransaction() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }

    @Test
    public void save_authorWithSuchIdPresents_notSaveEntity() {
        int authorsTotalNumberBefore = repository.findAll().size();

        Author entityToTest = repository.getById(ID_TO_PERSIST);
        repository.save(entityToTest);
        int authorsTotalNumberAfter = repository.findAll().size();

        Assert.assertEquals(authorsTotalNumberBefore, authorsTotalNumberAfter);
    }

    @Test
    public void save_authorWithSuchIdNotPresents_saveEntity() {
        Author entityToTest = new Author();
        entityToTest.setName("test_name_from_create");
        entityToTest.setSurname("test_surname_from_create");

        int authorsTotalNumberBefore = repository.findAll().size();
        repository.save(entityToTest);
        int authorsTotalNumberAfter = repository.findAll().size();

        Assert.assertEquals(authorsTotalNumberBefore + 1, authorsTotalNumberAfter);
    }

    @Test
    public void getById_authorWithSuchIdPresents_getEntity() {
        Author entityToTest = repository.getById(ID_TO_PERSIST);

        Assert.assertNotNull(entityToTest);
    }

    @Test(expected = RuntimeException.class)
    public void getById_authorWithSuchIdNotPresents_throwException() {
        repository.getById(0);
    }

    @Test
    public void update_authorWithSuchIdPresents_updateEntity() {
        Author entityToTest = repository.getById(ID_TO_PERSIST);
        String newNameToSet = "updated_name";
        entityToTest.setName(newNameToSet);

        repository.update(entityToTest);

        String updatedNameFromDB = repository.getById(entityToTest.getId()).getName();

        Assert.assertEquals(newNameToSet, updatedNameFromDB);
    }

    @Test(expected = RuntimeException.class)
    public void update_authorWithSuchIdNotPresents_throwException() {
        Author entityToTest = new Author();
        entityToTest.setName("test_name_from_update");

        repository.update(entityToTest);
    }

}
