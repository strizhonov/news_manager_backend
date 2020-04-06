package repositoryit;

import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepositoryImpl;
import org.junit.After;
import org.junit.Assert;
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
@ContextConfiguration(classes = {ITRepositoryConfig.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
public class TagRepositoryIT {

    private static final int TAG_TO_DELETE_ID = 10;
    private static final int EXISTING_TAG_ID = 1;

    @Autowired
    private TagRepositoryImpl repository;

    @After
    public void processTransaction() {
        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
    }

    @Test
    public void save_tagWithSuchIdPresents_notSaveEntity() {
        Tag entityToTest = repository.getById(EXISTING_TAG_ID);

        int tagsTotalNumberBeforeOperation = repository.findAll().size();
        repository.save(entityToTest);
        int tagsTotalNumberAfterOperation = repository.findAll().size();

        Assert.assertEquals(tagsTotalNumberBeforeOperation, tagsTotalNumberAfterOperation);
    }

    @Test
    public void save_tagWithSuchIdNotPresents_saveEntity() {
        Tag entityToTest = new Tag();
        entityToTest.setName("test_name_from_create");

        int tagsTotalNumberBeforeOperation = repository.findAll().size();
        repository.save(entityToTest);
        int tagsTotalNumberAfterOperation = repository.findAll().size();

        Assert.assertEquals(tagsTotalNumberBeforeOperation + 1, tagsTotalNumberAfterOperation);
    }

    @Test
    public void getById_tagWithSuchIdPresents_getEntity() {
        Tag entityToTest = repository.getById(EXISTING_TAG_ID);

        Assert.assertNotNull(entityToTest);
    }

    @Test(expected = RuntimeException.class)
    public void getById_tagWithSuchIdNotPresents_throwException() {
        repository.getById(0);
    }

    @Test
    public void update_tagWithSuchIdPresents_updateEntity() {
        Tag entityToTest = repository.getById(EXISTING_TAG_ID);
        String newNameToSet = "updated_name";
        entityToTest.setName(newNameToSet);

        repository.update(entityToTest);

        String updatedNameFromDB = repository.getById(entityToTest.getId()).getName();

        Assert.assertEquals(newNameToSet, updatedNameFromDB);
    }

    @Test(expected = RuntimeException.class)
    public void update_tagWithSuchIdNotPresents_throwException() {
        Tag entityToTest = new Tag();
        entityToTest.setName("test_name_from_update");

        repository.update(entityToTest);
    }

    @Test
    public void delete_presentInDatabase_removeEntity() {
        int tagsTotalNumberBeforeOperation = repository.findAll().size();

        Tag entityToDelete = repository.getById(TAG_TO_DELETE_ID);
        repository.delete(entityToDelete);
        int tagsTotalNumberAfterOperation = repository.findAll().size();

        Assert.assertEquals(tagsTotalNumberBeforeOperation - 1, tagsTotalNumberAfterOperation);
    }

}
