package uuster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uuster.domain.News;

public interface NewsRepository extends JpaRepository<News, Long> {

}
