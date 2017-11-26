package uuster.service;

import uuster.domain.Author;
import uuster.validator.SignUpForm;

public interface AuthorService {
    void save(SignUpForm signUpForm);
    Author findByUsername(String username);
}