package uuster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uuster.repository.NewsRepository;
import uuster.service.NewsService;
import uuster.validator.NewsForm;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    @Secured("ROLE_JOURNALIST")
    @GetMapping("/news/create")
    public String createArticleForm(){
        return "articleForm";
    }

    @Secured("ROLE_JOURNALIST")
    @PostMapping("/news/create")
    public String createArticle(@Validated NewsForm newsForm, @RequestParam MultipartFile file){
        newsService.createArticle(file, newsForm);
        return "redirect:/";
    }

    @GetMapping("/news/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        if(newsRepository.getOne(id) == null) return "redirect:/error";
        newsService.increaseCounterByOne(id);
        model.addAttribute("article", newsRepository.getOne(id));
        return "news";
    }
}
