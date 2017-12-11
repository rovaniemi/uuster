package uuster.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uuster.domain.News;
import uuster.domain.Tag;
import java.time.LocalDateTime;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByTagsContains(Tag tag, Pageable pageable);
    List<News> findTop10ByTimeBetweenOrderByCounterDesc(LocalDateTime start, LocalDateTime end);
}