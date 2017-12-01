package uuster.service;

import org.springframework.stereotype.Service;
import uuster.validator.NewsForm;

@Service
public class NewsService {


    public void createArticle(NewsForm newsForm){
        System.out.println(newsForm);
    }
}
