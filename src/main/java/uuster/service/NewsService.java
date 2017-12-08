package uuster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uuster.domain.*;
import uuster.repository.AuthorRepository;
import uuster.repository.NewsPictureRepository;
import uuster.repository.NewsRepository;
import uuster.repository.TagRepository;
import uuster.validator.NewsForm;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    public void createArticle(MultipartFile file, NewsForm newsForm, Author author){
        NewsPicture picture = new NewsPicture();
        Set<Tag> tagsSet = saveAndLoadTags(newsForm.getTags());
        News news = new News(newsForm.getTitle(), newsForm.getText(), newsForm.getLead(), tagsSet);
        news.getPictures().add(picture);
        news.getAuthors().add(author);
        if(file != null && !file.isEmpty()) {
            NewsPicture newsPicture = new NewsPicture();
            newsPicture.setNews(news);
            try {
                newsPicture.setContent(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            newsPicture.setContentLength(file.getSize());
            newsPicture.setContentType(file.getContentType());
            newsPicture.setName(file.getName());
            List<NewsPicture> newsPictures = new ArrayList<>();
            newsPictures.add(newsPicture);
            news.setPictures(newsPictures);
            newsPictureRepository.save(newsPicture);
            newsRepository.save(news);
        }
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
    public void createArticle(MultipartFile file, NewsForm newsForm) {
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
