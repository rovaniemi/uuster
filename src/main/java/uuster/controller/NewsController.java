package uuster.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {

    @Secured("ROLE_JOURNALIST")
    @GetMapping("/news/create")
    public String createArticleForm(){
        return "articleForm";
    }
}
