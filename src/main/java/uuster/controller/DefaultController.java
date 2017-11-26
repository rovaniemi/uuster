package uuster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uuster.repository.NewsRepository;
import uuster.service.AuthorService;
import uuster.service.SecurityService;
import uuster.validator.LoginForm;
import uuster.validator.SignUpForm;
import javax.validation.Valid;

@Controller
public class DefaultController {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private AuthorController authorController;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("*")
    public String list(Model model) {
        model.addAttribute("news", newsRepository.findAll());
        return "index";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid SignUpForm signUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        authorService.save(signUpForm);
        securityService.autologin(signUpForm.getUsername(), signUpForm.getConfirmPassword());
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
