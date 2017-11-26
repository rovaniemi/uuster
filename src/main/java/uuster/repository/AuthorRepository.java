package uuster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uuster.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByUsername(String string);
}
