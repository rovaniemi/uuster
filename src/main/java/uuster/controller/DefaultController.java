package uuster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uuster.repository.NewsRepository;
import uuster.validator.LoginForm;
import uuster.validator.SignUpForm;
import javax.validation.Valid;

@Controller
public class DefaultController {

    @Autowired
    private NewsRepository newsRepository;

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
    public String singUp(@Valid SignUpForm signUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        return "redirect:/login";
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
