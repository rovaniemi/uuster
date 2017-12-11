package uuster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uuster.domain.Author;
import uuster.domain.ProfilePicture;
import uuster.repository.AuthorRepository;
import uuster.repository.ProfilePictureRepository;
import uuster.repository.RoleRepository;
import uuster.validator.EditForm;
import uuster.validator.SignUpForm;

import java.io.IOException;
import java.util.HashSet;

@Transactional
@Service
public class CustomAuthorService implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ProfilePictureRepository profilePictureRepository;
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

    public void editAuthor(String username, MultipartFile file, EditForm editForm) {
        Author author = authorRepository.findByUsername(username);
        if(editForm.getFirstName() != null && !editForm.getFirstName().trim().isEmpty()) author.setFirstName(editForm.getFirstName());
        if(editForm.getLastName() != null && !editForm.getLastName().trim().isEmpty()) author.setLastName(editForm.getLastName());
        if(editForm.getEmail() != null && !editForm.getEmail().trim().isEmpty()) author.setEmail(editForm.getEmail());
        if(editForm.getDescription() != null && !editForm.getDescription().trim().isEmpty()) author.setDescription(editForm.getDescription());


        if(file != null && !file.isEmpty()) {
            ProfilePicture profilePicture = new ProfilePicture();
            profilePicture.setAuthor(author);
            try {
                profilePicture.setContent(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            profilePicture.setContentLength(file.getSize());
            profilePicture.setContentType(file.getContentType());
            profilePicture.setName(file.getName());
            author.setPicture(profilePicture);
            profilePictureRepository.saveAndFlush(profilePicture);
        }

        authorRepository.saveAndFlush(author);
    }
}