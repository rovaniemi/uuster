package uuster.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import uuster.domain.Role;
import uuster.repository.RoleRepository;

@Component
public class RoleConfiguration implements ApplicationRunner {

    private RoleRepository roleRepository;

    @Autowired
    public RoleConfiguration(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void run(ApplicationArguments args) {
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("JOURNALIST"));
    }
}