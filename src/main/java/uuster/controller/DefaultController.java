package uuster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uuster.repository.TagRepository;
import uuster.service.AuthorServiceInterface;
import uuster.service.NewsService;
import uuster.service.SecurityServiceInterface;
import uuster.validator.LoginForm;
import uuster.validator.SignUpForm;
import javax.validation.Valid;

@Transactional
@Controller
public class DefaultController {

    @Autowired
    private AuthorServiceInterface authorServiceInterface;

    @Autowired
    private SecurityServiceInterface securityServiceInterface;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private NewsService newsService;

    @GetMapping("*")
    public String list(@RequestParam(value="tag", required = false) String tag,
                       @RequestParam(value="page", required = false) String page,
                       Model model) {

        if(tagRepository.findByName(tag) == null) {
            model.addAttribute("tag", "Top stories");
        } else {
            model.addAttribute("tag", tagRepository.findByName(tag).getName());
        }
        model.addAttribute("news", newsService.getNews(tag, page));
        model.addAttribute("top", newsService.getTop());
        model.addAttribute("page", newsService.getPage(page));

        return "index";
    }

    @GetMapping("/signup")
    public String signUpPage(@ModelAttribute SignUpForm signUpForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute SignUpForm signUpForm, BindingResult bindingResult) {
        if (authorServiceInterface.findByUsername(signUpForm.getUsername()) != null){
            bindingResult.addError(new FieldError("Author", "username","Username is already taken"));
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        authorServiceInterface.save(signUpForm);
        securityServiceInterface.autologin(signUpForm.getUsername(), signUpForm.getConfirmPassword());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        return "redirect:/";
    }
}
