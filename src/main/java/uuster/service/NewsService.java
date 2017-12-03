package uuster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uuster.domain.Author;
import uuster.domain.News;
import uuster.domain.NewsPicture;
import uuster.domain.Tag;
import uuster.repository.AuthorRepository;
import uuster.repository.NewsPictureRepository;
import uuster.repository.NewsRepository;
import uuster.repository.TagRepository;
import uuster.validator.NewsForm;

import javax.xml.bind.DatatypeConverter;
import java.util.HashSet;
import java.util.Set;

@Service
public class NewsService {

    @Autowired
    private NewsPictureRepository newsPictureRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CustomSecurityService customSecurityService;

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public void createArticle(String file, NewsForm newsForm, Author author){
        NewsPicture picture = new NewsPicture();
        byte[] imagedata = DatatypeConverter.parseBase64Binary(file.substring(file.indexOf(",") + 1));
        picture.setName("upload.jpeg");
        picture.setContentType("image/jpeg");
        picture.setContentLength(new Long(imagedata.length));
        picture.setContent(imagedata);
        Set<Tag> tagsSet = saveAndLoadTags(newsForm.getTags());
        News news = new News(newsForm.getTitle(), newsForm.getText(), newsForm.getLead(), tagsSet);
        news.getPictures().add(picture);
        news.getAuthors().add(author);
        picture.setNews(newsRepository.save(news));
        newsPictureRepository.save(picture);
    }

    @Transactional
    public Set<Tag> saveAndLoadTags(String tags) {
        String[] tagStrings = tags.split(",");
        Set<Tag> tagSet = new HashSet<>();
        for (String tag : tagStrings) {
            Tag t = tagRepository.findByName(tag.toLowerCase().trim());
            if (t == null) {
                t = new Tag();
                t.setName(tag.toLowerCase().trim());
                t = tagRepository.save(t);
            }
            tagSet.add(t);
        }
        return tagSet;
    }

    @Transactional
    public void createArticle(String file, NewsForm newsForm) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        createArticle(file, newsForm, authorRepository.findByUsername(username));
    }


}
