package uuster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uuster.domain.NewsPicture;

public interface NewsPictureRepository extends JpaRepository<NewsPicture, Long> {

}
