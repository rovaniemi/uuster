package uuster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uuster.domain.ProfilePicture;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {

}
