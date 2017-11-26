package uuster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uuster.domain.Role;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByName(String string);
}
