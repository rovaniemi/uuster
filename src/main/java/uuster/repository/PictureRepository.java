package uuster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uuster.domain.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {

}
