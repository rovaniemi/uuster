package uuster.domain;

import org.junit.*;

import java.util.ArrayList;
import java.util.HashSet;

public class AuthorTest {

    public AuthorTest(){

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getterAndSetter(){
        Author author = new Author("Mikko-Pekka","Lastname","Username","something@gmail.com","password123",new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        Assert.assertEquals("Mikko-Pekka", author.getFirstName());
        Assert.assertEquals("Lastname", author.getLastName());
        Assert.assertEquals("Username", author.getUsername());
        Assert.assertEquals("something@gmail.com",author.getEmail());
        Assert.assertEquals("password123",author.getPassword());
    }
}