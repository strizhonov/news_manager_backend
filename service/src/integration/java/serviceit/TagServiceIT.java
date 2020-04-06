package serviceit;

import com.epam.lab.dto.TagDto;
import com.epam.lab.service.TagService;
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
@ContextConfiguration(classes = {ITServiceConfig.class}, loader = AnnotationConfigContextLoader.class)
public class TagServiceIT {

    private static final int TAG_TO_DELETE_ID = 10;
    private static final int EXISTING_TAG_ID = 1;

    @Autowired
    private TagService tagService;

    @Test
    public void create_existingByNameTag_entityNotSaved() {
        TagDto dtoToTest = tagService.getById(EXISTING_TAG_ID);
        int tagsTotalNumberBeforeOperation = tagService.findAll().size();
        tagService.create(dtoToTest);
        int tagsTotalNumberAfterOperation = tagService.findAll().size();

        Assert.assertEquals(tagsTotalNumberBeforeOperation, tagsTotalNumberAfterOperation);
    }

    @Test
    public void create_nonExistingByNameTag_entitySaved() {
        TagDto dtoToTest = new TagDto();
        dtoToTest.setName("test_name_from_create");
        int tagsTotalNumberBeforeOperation = tagService.findAll().size();
        tagService.create(dtoToTest);
        int tagsTotalNumberAfterOperation = tagService.findAll().size();

        Assert.assertEquals(tagsTotalNumberBeforeOperation + 1, tagsTotalNumberAfterOperation);
    }

    @Test
    public void getById_idPresentsInDatabase_getEntity() {
        TagDto dtoToTest = tagService.getById(EXISTING_TAG_ID);

        Assert.assertNotNull(dtoToTest);
    }

    @Test(expected = RuntimeException.class)
    public void getById_idNotPresentsInDatabase_throwException() {
        tagService.getById(0);
    }

    @Test
    public void update_existingByIdTag_updateEntity() {
        TagDto dtoToTest = tagService.getById(EXISTING_TAG_ID);
        String newNameToSet = "updated_name";
        dtoToTest.setName(newNameToSet);
        tagService.update(dtoToTest);
        String updatedNameFromDB = tagService.getById(dtoToTest.getId()).getName();

        Assert.assertEquals(newNameToSet, updatedNameFromDB);
    }

    @Test(expected = RuntimeException.class)
    public void update_existingByIdTag_throwException() {
        TagDto dtoToTest = new TagDto();
        dtoToTest.setName("test_name_from_update");
        tagService.update(dtoToTest);
    }

    @Test
    public void delete_idPresentsInDatabase_removeTag() {
        int tagsTotalNumberBeforeOperation = tagService.findAll().size();
        tagService.delete(TAG_TO_DELETE_ID);
        int tagsTotalNumberAfterOperation = tagService.findAll().size();

        Assert.assertEquals(tagsTotalNumberBeforeOperation - 1, tagsTotalNumberAfterOperation);
    }

    @Test(expected = RuntimeException.class)
    public void delete_idNotPresentsInDatabase_throwException() {
        tagService.delete(0);
    }

}
