package uuster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uuster.repository.NewsRepository;
import uuster.service.NewsService;
import uuster.validator.ArticleEdit;
import uuster.validator.NewsForm;
import uuster.validator.SignUpForm;

import javax.validation.Valid;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsRepository newsRepository;

    @Secured("ROLE_JOURNALIST")
    @GetMapping("/news/create")
    public String createArticleForm(@ModelAttribute NewsForm newsForm){
        return "articleForm";
    }

    @Secured("ROLE_JOURNALIST")
    @PostMapping("/news/create")
    public String createArticle(@Valid @ModelAttribute NewsForm newsForm, BindingResult bindingResult){
        if (newsForm.getFile() == null || newsForm.getFile().getSize() < 10 || newsForm.getFile().getSize() >= 10485760 || !newsForm.getFile().getContentType().startsWith("image/")){
            bindingResult.addError(new FieldError("File", "file","Invalid File"));
        }
        if (bindingResult.hasErrors()) {
            return "articleForm";
        }
        newsService.createArticle(newsForm);
        return "redirect:/";
    }

    @GetMapping("/news/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        if(newsRepository.getOne(id) == null) return "redirect:/error";
        newsService.increaseCounterByOne(id);
        model.addAttribute("article", newsRepository.getOne(id));
        return "news";
    }

    @Secured("ROLE_JOURNALIST")
    @PostMapping("/news/{id}/delete")
    public String deleteArticle(@PathVariable Long id, Model model) {
        newsService.deleteArticle(id);
        return "redirect:/";
    }

    @Secured("ROLE_JOURNALIST")
    @PostMapping("/news/{id}/edit")
    public String editArticle(@PathVariable Long id,@Validated ArticleEdit articleEdit, @RequestParam MultipartFile file) {
        newsService.editArticle(id, articleEdit, file);
        return "redirect:/news/" + id;
    }

    @Secured("ROLE_JOURNALIST")
    @GetMapping("/news/{id}/edit")
    public String getEditArticle(@PathVariable Long id, Model model) {
        model.addAttribute("article", newsRepository.getOne(id));
        model.addAttribute("tags", newsService.getTags(id));
        return "articleEdit";
    }
}
