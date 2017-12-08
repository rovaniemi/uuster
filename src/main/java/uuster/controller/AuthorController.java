package uuster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uuster.domain.Author;
import uuster.repository.AuthorRepository;
import uuster.service.AuthorService;
import uuster.service.CustomAuthorService;
import uuster.service.CustomSecurityService;
import uuster.validator.EditForm;

@Controller
public class AuthorController {

    @Autowired
    private CustomSecurityService customSecurityService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CustomAuthorService customAuthorService;

    @GetMapping("/profile")
    @Secured("ROLE_JOURNALIST")
    public String getProfile(Model model) {
        return "redirect:/profile/" + customSecurityService.findLoggedInUsername();
    }

    @GetMapping("/profile/{username}")
    public String getProfile(@PathVariable String username, Model model) {
        Author author = authorRepository.findByUsername(username);
        model.addAttribute("author", author);
        if(authorRepository.findByUsername(username).getPicture() != null){
            model.addAttribute("picture", "/images/profilepicture/" + authorRepository.findByUsername(username).getPicture().getId());
        } else {
            model.addAttribute("picture", "/assets/pic/default.png");
        }
        return "profile";
    }

    @GetMapping("/profile/{username}/edit")
    public String editProfile(@PathVariable String username, Model model) {
        if(customSecurityService.findLoggedInUsername().equals(username)) {
            model.addAttribute("author", authorRepository.findByUsername(username));
            if(authorRepository.findByUsername(username).getPicture() != null){
                model.addAttribute("picture", "/images/profilepicture/" + authorRepository.findByUsername(username).getPicture().getId());
            } else {
                model.addAttribute("picture", "/assets/pic/default.png");
            }

            return "profileForm";
        }
        return "redirect:/error";
    }

    @PostMapping("/profile/{username}/edit")
    @Secured("ROLE_JOURNALIST")
    public String editProfile(@PathVariable String username, @Validated EditForm editForm, @RequestParam MultipartFile file) {
        if(customSecurityService.findLoggedInUsername().equals(username)) {
            customAuthorService.editAuthor(username, file, editForm);
            return "redirect:/profile/" + username + "";
        }
        return "redirect:/error";
    }
}
