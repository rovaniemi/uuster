package uuster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uuster.domain.Author;
import uuster.repository.AuthorRepository;
import uuster.repository.RoleRepository;
import uuster.validator.SignUpForm;

import java.util.HashSet;

@Service
public class CustomAuthorService implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(SignUpForm signUpForm) {
        Author author = new Author();
        author.setEmail(signUpForm.getEmail());
        author.setFirstName(signUpForm.getFirstName());
        author.setLastName(signUpForm.getLastName());
        author.setUsername(signUpForm.getUsername());
        author.setPassword(bCryptPasswordEncoder.encode(signUpForm.getPassword()));
        author.setRoles(new HashSet<>(roleRepository.findByName("JOURNALIST")));
        authorRepository.save(author);
    }

    @Override
    public Author findByUsername(String username) {
        return authorRepository.findByUsername(username);
    }
}