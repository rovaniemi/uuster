package uuster.service;

import uuster.domain.Author;
import uuster.validator.SignUpForm;

public interface AuthorServiceInterface {
    void save(SignUpForm signUpForm);
    Author findByUsername(String username);
}