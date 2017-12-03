package uuster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    public String createArticle(@RequestParam("file") String file, @Validated NewsForm newsForm){
        newsService.createArticle(file, newsForm);
        return "redirect:/";
    }

    @GetMapping("/news/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        return "news";
    }
}
