package uuster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uuster.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String string);
}
