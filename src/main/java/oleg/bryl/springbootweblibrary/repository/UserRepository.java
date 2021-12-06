package oleg.bryl.springbootweblibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import oleg.bryl.springbootweblibrary.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     *
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);
}
