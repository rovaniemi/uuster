package uuster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uuster.repository.NewsRepository;

@Controller
public class DefaultController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("*")
    public String list(Model model) {
        model.addAttribute("news", newsRepository.findAll());
        return "index";
    }
}
