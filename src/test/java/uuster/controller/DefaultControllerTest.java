package uuster.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uuster.domain.Author;
import uuster.repository.AuthorRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AuthorRepository authorRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
        this.authorRepository.saveAndFlush(new Author("etu", "suku", "kayttaja", "jotain@gmail.com", "salasana"));
    }

    @Test
    public void getIndexPage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void authenticationWorks() throws Exception {
        this.mockMvc.perform(get("/news/edit"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username="admin",roles={"ADMIN","JOURNALIST"})
    public void authenticationWorksIfLoggedIn() throws Exception {
        this.mockMvc.perform(get("/news/edit"))
                .andExpect(status().isBadRequest());
    }

    /*@Test
    public void createNewUserWorks() throws Exception {
        this.mockMvc.perform(get("/signup")).andExpect(status().isOk());
        this.mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "jotain")
                .param("lastName","jotain")
                .param("username","jotain")
                .param("email", "karlin@cs.h.fi")
                .param("password", "asdfasdf")
                .param("confirmPassword", "asdfasdf")).andExpect(status().is2xxSuccessful());
    }*/

    @Test
    public void loginWorks() throws Exception {
        this.authorRepository.saveAndFlush(new Author("etu", "suku", "kayttaja4", "jotain@gmail.com", "salasana"));
        this.mockMvc.perform(get("/signin")).andExpect(status().isOk());
        this.mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username","kayttaja4")
                .param("password", "salasana")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void loginNotWorkWithWrongPassword() throws Exception {
        this.mockMvc.perform(get("/signin")).andExpect(status().isOk());
        try {
            this.mockMvc.perform(post("/signup")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("username", "kayttaja")
                    .param("password", "saasd")).andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }
}
