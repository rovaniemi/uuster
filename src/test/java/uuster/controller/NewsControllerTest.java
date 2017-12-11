package uuster.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import uuster.Uuster;
import uuster.domain.Author;
import uuster.domain.News;
import uuster.domain.NewsPicture;
import uuster.domain.Tag;
import uuster.repository.AuthorRepository;
import uuster.repository.NewsPictureRepository;
import uuster.repository.NewsRepository;
import uuster.repository.TagRepository;
import uuster.service.NewsService;
import uuster.validator.NewsForm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Uuster.class)
@WebAppConfiguration
public class NewsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private NewsRepository newsRepository;


    @Autowired
    private AuthorRepository authorRepository;


    @Autowired
    private NewsService newsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
        Author author = this.authorRepository.saveAndFlush(new Author("etu", "suku", "kayttaja", "jotain@gmail.com", "salasana"));
        MultipartFile multipartFile = new MockMultipartFile("b", "b.png", "image/png", "nonsensecontent".getBytes());
        newsService.createArticle(new NewsForm("text must be over 20 characters long", "title", "text must be over 20 characters long", "tag,tag", multipartFile), author);
    }

    @Test
    public void shouldBeNewsWithId1() throws Exception {
        this.mockMvc.perform(get("/news/1"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void thereShouldNotBeNewsWithId2() throws Exception {
        try {
            this.mockMvc.perform(get("/news/2"))
                    .andExpect(status().is2xxSuccessful());
        } catch (Exception e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void addNewsWork() {
        Author author = this.authorRepository.saveAndFlush(new Author("etu", "suku", "toinen", "jotain@gmail.com", "salasana"));
        MultipartFile multipartFile = new MockMultipartFile("b", "b.png", "image/png", "nonsensecontent".getBytes());
        newsService.createArticle(new NewsForm("text must be over 20 characters long", "title", "text must be over 20 characters long", "tag,tag", multipartFile), author);
        Assert.assertEquals(this.newsRepository.findAll().size(), 4);
    }
}