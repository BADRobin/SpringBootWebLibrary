package oleg.bryl.springbootweblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import oleg.bryl.springbootweblibrary.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRolename(String rolename);
}
