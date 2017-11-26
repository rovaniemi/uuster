package uuster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uuster.domain.Role;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByName(String string);
}
