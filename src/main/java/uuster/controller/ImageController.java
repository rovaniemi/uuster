package uuster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uuster.domain.NewsPicture;
import uuster.domain.ProfilePicture;
import uuster.repository.NewsPictureRepository;
import uuster.repository.ProfilePictureRepository;

@Transactional
@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private NewsPictureRepository newsPictureRepository;

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @RequestMapping(value = "/newspicture/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> viewNewsPicture(@PathVariable Long id) {
        NewsPicture fo = newsPictureRepository.findOne(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo.getContentType()));
        headers.setContentLength(fo.getContentLength());

        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);
    }

    @GetMapping("/profilepicture/{id}")
    public ResponseEntity<byte[]> viewProfilePicture(@PathVariable Long id) {
        ProfilePicture fo = profilePictureRepository.findOne(id);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fo.getContentType()));
        headers.setContentLength(fo.getContentLength());

        return new ResponseEntity<>(fo.getContent(), headers, HttpStatus.CREATED);
    }
}
