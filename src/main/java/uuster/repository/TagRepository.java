package uuster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uuster.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String tag);
}

