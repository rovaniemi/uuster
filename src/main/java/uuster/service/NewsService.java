package uuster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import uuster.validator.ArticleEdit;
import uuster.validator.NewsForm;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
public class NewsService {

    @Autowired
    private NewsPictureRepository newsPictureRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public void createArticle(NewsForm newsForm, Author author){
        NewsPicture picture = new NewsPicture();
        Set<Tag> tagsSet = saveAndLoadTags(newsForm.getTags());
        News news = new News(newsForm.getTitle(), newsForm.getText(), newsForm.getLead(), tagsSet);
        news.getPictures().add(picture);
        news.getAuthors().add(author);
        if(newsForm.getFile() != null) {
            savePicture(news,newsForm.getFile());
        }
    }

    public Set<Tag> saveAndLoadTags(String tags) {
        String[] tagStrings = Arrays.stream(tags.split(",")).filter(e -> !e.trim().isEmpty()).toArray(String[]::new);
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

    public void createArticle(NewsForm newsForm) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        createArticle(newsForm, authorRepository.findByUsername(username));
    }

    public List<News> getNews(String tag, String page) {
        if(isParsable(page) && tagRepository.findByName(tag) != null) {
            return listWithTag(Integer.parseInt(page), tagRepository.findByName(tag));
        } else if (isParsable(page)) {
            return list(Integer.parseInt(page));
        } else if (tagRepository.findByName(tag) != null) {
            return listWithTag(0, tagRepository.findByName(tag));
        } else {
            return list(0);
        }
    }

    public List<News> list(Integer page) {
        Pageable pageable = new PageRequest(page, 5);
        return newsRepository.findAll(pageable).getContent();
    }

    public List<News> listWithTag(Integer page, Tag tag) {
        Pageable pageable = new PageRequest(page, 5);
        return newsRepository.findAllByTagsContains(tag, pageable);
    }

    public int getPage(String string) {
        if(isParsable(string)) return Integer.parseInt(string);
        else return 0;
    }

    public boolean isParsable(String input){
        boolean parsable = true;
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            parsable = false;
        }
        return parsable;
    }

    public void increaseCounterByOne(long id) {
        News news = this.newsRepository.getOne(id);
        news.setCounter(news.getCounter() + 1);
        this.newsRepository.saveAndFlush(news);
    }

    public List<News> getTop() {
        return this.newsRepository.findTop10ByTimeBetweenOrderByCounterDesc(LocalDateTime.now().minusWeeks(1), LocalDateTime.now());
    }

    public void deleteArticle(Long id) {
        News news = newsRepository.getOne(id);
        news.getPictures().stream().forEach(newsPicture -> newsPictureRepository.delete(newsPicture.getId()));
        newsRepository.delete(news);
    }

    public void editArticle(long id, ArticleEdit articleEdit) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        editArticle(articleEdit, authorRepository.findByUsername(username), id);
    }

    public void editArticle(ArticleEdit articleEdit, Author author, long id) {
        News news = newsRepository.getOne(id);
        news.setTags(saveAndLoadTags(articleEdit.getTags()));
        news.setText(articleEdit.getText());
        if(news.getAuthors().stream().filter(e -> e.getUsername().equals(author.getUsername())).count() == 0) news.getAuthors().add(author);
        news.setTitle(articleEdit.getTitle());
        news.setLead(articleEdit.getLead());
        if(articleEdit.getFile() != null && !articleEdit.getFile().isEmpty()) {
            news.getPictures().stream().forEach(e -> newsPictureRepository.delete(e.getId()));
            savePicture(news, articleEdit.getFile());
        }
    }

    public String getTags(long id) {
        String tagString = "";
        News news = newsRepository.getOne(id);
        Tag[] tags = news.getTags().toArray(new Tag[news.getTags().size()]);
        for (int i = 0; i < tags.length; i++) {
            if(i < tags.length - 1) {
                tagString += tags[i].getName() + ", ";
            } else {
                tagString += tags[i].getName();
            }
        }
        return tagString;
    }

    public void savePicture(News news, MultipartFile file) {
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
