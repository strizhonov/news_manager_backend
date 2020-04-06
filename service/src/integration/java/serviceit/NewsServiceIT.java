package serviceit;

import com.epam.lab.comparator.NewsSortType;
import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.NewsSearchParamDto;
import com.epam.lab.dto.TagDto;
import com.epam.lab.service.AuthorService;
import com.epam.lab.service.NewsService;
import com.epam.lab.service.TagService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.*;

/**
 * <code>@Test</code>-annotated methods naming schema:
 * testedMethod_passedParamOrCondition_testedMethodShouldDoUnderCondition
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITServiceConfig.class, loader = AnnotationConfigContextLoader.class)
public class NewsServiceIT {

    private static final int NEWS_TO_DELETE_ID = 1;
    private static final int EXISTING_NEWS_ID = 2;
    private static final int NEWS_WITH_MORE_THAN_ONE_TAG_ID = 5;
    private static final int EXISTING_AUTHOR_ID = 7;

    @Autowired
    private NewsService newsService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private TagService tagService;

    private TagDto firstNotExistedTag;
    private TagDto secondNotExistedTag;
    private TagDto thirdNotExistedTag;
    private TagDto fourthNotExistedTag;
    private TagDto fifthNotExistedTag;
    private AuthorDto firstNotExistedAuthor;
    private AuthorDto secondNotExistedAuthor;
    private AuthorDto thirdNotExistedAuthor;

    @Test
    public void create_existingNews_entitySaved() {
        NewsDto dtoToTest = newsService.getById(EXISTING_NEWS_ID);
        int newsTotalNumberBefore = newsService.count();
        newsService.create(dtoToTest);
        int newsTotalNumberAfter = newsService.count();

        Assert.assertEquals(newsTotalNumberBefore + 1, newsTotalNumberAfter);
    }

    @Test
    public void create_nonExistingNewsWithNonExistingTagsAndNonExistingAuthor_saveAllEntities() {
        NewsDto newsDtoToTest = new NewsDto.Builder()
                .title("first_new_news_title")
                .shortText("first_new_news_short_text")
                .fullText("first_new_news_full_text")
                .creationDate(new Date())
                .modificationDate(new Date())
                .author(firstNotExistedAuthor)
                .tags(new HashSet<>(Arrays.asList(firstNotExistedTag, secondNotExistedTag)))
                .build();

        int tagsNumberBefore = tagService.findAll().size();
        int authorsNumberBefore = authorService.findAll().size();
        int newsNumberBefore = newsService.count();

        newsService.create(newsDtoToTest);

        int tagsNumberAfter = tagService.findAll().size();
        int authorsNumberAfter = authorService.findAll().size();
        int newsNumberAfter = newsService.count();

        Assert.assertEquals(tagsNumberBefore + 2, tagsNumberAfter);
        Assert.assertEquals(authorsNumberBefore + 1, authorsNumberAfter);
        Assert.assertEquals(newsNumberBefore + 1, newsNumberAfter);
    }

    @Test
    public void create_nonExistingNewsWithNonExistingTagsAndExistingAuthor_saveNewsAndTagsButNotSaveAuthor() {
        AuthorDto existingAuthorToAddToNews = authorService.getById(EXISTING_AUTHOR_ID);
        NewsDto newsDtoToTest = new NewsDto.Builder()
                .title("second_new_news_title")
                .shortText("second_new_news_short_text")
                .fullText("second_new_news_full_text")
                .creationDate(new Date())
                .modificationDate(new Date())
                .author(existingAuthorToAddToNews)
                .tags(new HashSet<>(Arrays.asList(thirdNotExistedTag, fourthNotExistedTag)))
                .build();

        int tagsNumberBefore = tagService.findAll().size();
        int authorsNumberBefore = authorService.findAll().size();
        int newsNumberBefore = newsService.count();

        newsService.create(newsDtoToTest);

        int tagsNumberAfter = tagService.findAll().size();
        int authorsNumberAfter = authorService.findAll().size();
        int newsNumberAfter = newsService.count();

        Assert.assertEquals(tagsNumberBefore + 2, tagsNumberAfter);
        Assert.assertEquals(authorsNumberBefore, authorsNumberAfter);
        Assert.assertEquals(newsNumberBefore + 1, newsNumberAfter);
    }

    @Test
    public void create_nonExistingNewsWithExistingTagsAndNonExistingAuthor_saveNewsAndAuthorButNotSaveTags() {
        TagDto firstPresentTag = tagService.getById(1);
        TagDto secondPresentTag = tagService.getById(2);
        NewsDto newsDtoToTest = new NewsDto.Builder()
                .title("third_new_news_title")
                .shortText("third_new_news_short_text")
                .fullText("third_new_news_full_text")
                .creationDate(new Date())
                .modificationDate(new Date())
                .author(secondNotExistedAuthor)
                .tags(new HashSet<>(Arrays.asList(firstPresentTag, secondPresentTag)))
                .build();

        int tagsNumberBefore = tagService.findAll().size();
        int authorsNumberBefore = authorService.findAll().size();
        int newsNumberBefore = newsService.count();

        newsService.create(newsDtoToTest);

        int tagsNumberAfter = tagService.findAll().size();
        int authorsNumberAfter = authorService.findAll().size();
        int newsNumberAfter = newsService.count();

        Assert.assertEquals(tagsNumberBefore, tagsNumberAfter);
        Assert.assertEquals(authorsNumberBefore + 1, authorsNumberAfter);
        Assert.assertEquals(newsNumberBefore + 1, newsNumberAfter);
    }

    @Test
    public void create_nonExistingNewsWithExistingTagsAndExistingAuthor_saveNewsButNotSaveTagsAndAuthor() {
        TagDto firstPresentTag = tagService.getById(1);
        TagDto secondPresentTag = tagService.getById(2);
        AuthorDto existingAuthorToAddToNews = authorService.getById(1);
        NewsDto newsDtoToTest = new NewsDto.Builder()
                .title("fourth_new_news_title")
                .shortText("fourth_new_news_short_text")
                .fullText("fourth_new_news_full_text")
                .creationDate(new Date())
                .modificationDate(new Date())
                .author(existingAuthorToAddToNews)
                .tags(new HashSet<>(Arrays.asList(firstPresentTag, secondPresentTag)))
                .build();

        int tagsNumberBefore = tagService.findAll().size();
        int authorsNumberBefore = authorService.findAll().size();
        int newsNumberBefore = newsService.count();

        newsService.create(newsDtoToTest);

        int tagsNumberAfter = tagService.findAll().size();
        int authorsNumberAfter = authorService.findAll().size();
        int newsNumberAfter = newsService.count();

        Assert.assertEquals(tagsNumberBefore, tagsNumberAfter);
        Assert.assertEquals(authorsNumberBefore, authorsNumberAfter);
        Assert.assertEquals(newsNumberBefore + 1, newsNumberAfter);
    }

    @Test
    public void getById_entityIdExists_getEntity() {
        NewsDto dtoToTest = newsService.getById(EXISTING_NEWS_ID);

        Assert.assertNotNull(dtoToTest);
    }

    @Test(expected = RuntimeException.class)
    public void getById_entityIdNotExists_throwException() {
        newsService.getById(0);
    }

    @Test
    public void update_newTitleForExistingNews_updateNewsTitle() {
        NewsDto dtoToTest = newsService.getById(EXISTING_NEWS_ID);
        String updatedTitle = "updated_title";
        dtoToTest.setTitle(updatedTitle);
        newsService.update(dtoToTest);
        NewsDto updatedDto = newsService.getById(EXISTING_NEWS_ID);

        Assert.assertEquals(updatedTitle, updatedDto.getTitle());
    }

    @Test
    public void update_newExistingAuthorForExistingNews_updateNewsAuthor() {
        AuthorDto anyExistingAuthor = authorService.getById(EXISTING_AUTHOR_ID);
        NewsDto newsDtoToTest = newsService.getById(EXISTING_NEWS_ID);

        Assert.assertNotEquals(anyExistingAuthor, newsDtoToTest.getAuthor());

        newsDtoToTest.setAuthor(anyExistingAuthor);
        newsService.update(newsDtoToTest);
        NewsDto updatedDto = newsService.getById(EXISTING_NEWS_ID);

        Assert.assertEquals(newsDtoToTest.getAuthor(), updatedDto.getAuthor());

    }

    @Test
    public void update_newNotExistingAuthorForExistingNews_createAuthorAndUpdateNews() {
        NewsDto dtoToTest = newsService.getById(EXISTING_NEWS_ID);

        Assert.assertNotEquals(thirdNotExistedAuthor, dtoToTest.getAuthor());

        dtoToTest.setAuthor(thirdNotExistedAuthor);
        int authorsNumberBefore = authorService.findAll().size();
        newsService.update(dtoToTest);
        NewsDto updatedDto = newsService.getById(EXISTING_NEWS_ID);
        int authorsNumberAfter = authorService.findAll().size();

        Assert.assertEquals(dtoToTest.getAuthor().getName(), updatedDto.getAuthor().getName());
        Assert.assertEquals(dtoToTest.getAuthor().getSurname(), updatedDto.getAuthor().getSurname());
        Assert.assertEquals(authorsNumberBefore + 1, authorsNumberAfter);
    }

    @Test
    public void update_existingNewsWithFewerNumberOfTags_updateNewsTags() {
        NewsDto dtoToTest = newsService.getById(NEWS_WITH_MORE_THAN_ONE_TAG_ID);

        Assert.assertTrue(dtoToTest.getTags().size() > 1);

        dtoToTest.setTags(new HashSet<>(Collections.singletonList(fifthNotExistedTag)));
        newsService.update(dtoToTest);
        NewsDto updatedDto = newsService.getById(NEWS_WITH_MORE_THAN_ONE_TAG_ID);

        Assert.assertEquals(1, updatedDto.getTags().size());
    }

    @Test(expected = RuntimeException.class)
    public void update_newsNotExist_throwException() {
        newsService.update(new NewsDto());
    }

    @Test
    public void delete_newsIdExist_removeNews() {
        int newsTotalNumberBefore = newsService.count();
        newsService.delete(NEWS_TO_DELETE_ID);
        int newsTotalNumberAfter = newsService.count();

        Assert.assertEquals(newsTotalNumberBefore - 1, newsTotalNumberAfter);
    }

    @Test(expected = RuntimeException.class)
    public void delete_newsIdNotExist_throwException() {
        newsService.delete(0);
    }


    @Test
    public void count_always_getSameValueAsAllNewsListSize() {
        Assert.assertEquals(newsService.findAll().size(), newsService.count());
    }

    @Test
    public void search_paramContainsNothing_findAll() {
        NewsSearchParamDto paramDtoToUse = new NewsSearchParamDto();
        List<NewsDto> foundNews = newsService.search(paramDtoToUse);
        Assert.assertTrue(foundNews.size() > 0);
        Assert.assertEquals(foundNews.size(), newsService.count());
    }

    @Test
    public void search_paramContainsTag_findAllNewsWithNeededTag() {
        NewsSearchParamDto paramDtoToUse = new NewsSearchParamDto();
        paramDtoToUse.setTags(new HashSet<>(Collections.singletonList("tag_two")));
        List<NewsDto> foundNews = newsService.search(paramDtoToUse);
        Assert.assertEquals(3, foundNews.size());
    }

    @Test
    public void search_paramContainsAuthor_findAllNewsWithNeededAuthor() {
        String searchedAuthorName = "author_one_name";
        String searchedAuthorSurName = "author_one_surname";

        NewsSearchParamDto paramDtoToUse = new NewsSearchParamDto();
        AuthorDto authorToFind = new AuthorDto();
        authorToFind.setName(searchedAuthorName);
        authorToFind.setSurname(searchedAuthorSurName);
        paramDtoToUse.setAuthorDto(authorToFind);

        List<NewsDto> foundNews = newsService.search(paramDtoToUse);
        Assert.assertFalse(foundNews.isEmpty());
        for (NewsDto newsItem : foundNews) {
            Assert.assertEquals(searchedAuthorName, newsItem.getAuthor().getName());
            Assert.assertEquals(searchedAuthorSurName, newsItem.getAuthor().getSurname());
        }
    }

    @Test
    public void sort_paramContainsDateSortParam_getListSortedByCreationDate() {
        NewsSearchParamDto paramDtoToUse = new NewsSearchParamDto();
        paramDtoToUse.setSort(new LinkedHashSet<>(Collections.singletonList(NewsSortType.CREATION_DATE)));
        List<NewsDto> foundNews = newsService.search(paramDtoToUse);
        Assert.assertTrue(foundNews.get(0).getCreationDate().compareTo(foundNews.get(1).getCreationDate()) <= 0);
        Assert.assertTrue(foundNews.get(2).getCreationDate().compareTo(foundNews.get(3).getCreationDate()) <= 0);
        Assert.assertTrue(foundNews.get(4).getCreationDate().compareTo(foundNews.get(5).getCreationDate()) <= 0);
        Assert.assertTrue(foundNews.get(5).getCreationDate().compareTo(foundNews.get(7).getCreationDate()) <= 0);
    }


    @Before
    public void initEntities() {
        firstNotExistedTag = new TagDto.Builder()
                .name("first_new_tag_name")
                .build();
        secondNotExistedTag = new TagDto.Builder()
                .name("second_new_tag_name")
                .build();
        thirdNotExistedTag = new TagDto.Builder()
                .name("third_new_tag_name")
                .build();
        fourthNotExistedTag = new TagDto.Builder()
                .name("fourth_new_tag_name")
                .build();
        fifthNotExistedTag = new TagDto.Builder()
                .name("fifth_new_tag_name")
                .build();
        firstNotExistedAuthor = new AuthorDto.Builder()
                .name("first_new_author_name")
                .surname("first_new_author_surname")
                .build();
        secondNotExistedAuthor = new AuthorDto.Builder()
                .name("second_new_author_name")
                .surname("second_new_author_surname")
                .build();
        thirdNotExistedAuthor = new AuthorDto.Builder()
                .name("third_new_author_name")
                .surname("third_new_author_surname")
                .build();
    }

}
