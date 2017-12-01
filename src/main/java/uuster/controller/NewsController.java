package uuster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uuster.repository.AuthorRepository;
import uuster.repository.NewsRepository;
import uuster.service.NewsService;
import uuster.validator.NewsForm;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Secured("ROLE_JOURNALIST")
    @GetMapping("/news/create")
    public String createArticleForm(){
        return "articleForm";
    }

    @Secured("ROLE_JOURNALIST")
    @PostMapping("/news/create")
    public String createArticle(@RequestBody @Validated NewsForm newsForm){
        newsService.createArticle(newsForm);
        return "redirect:/";
    }
}
