package antifraud.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameIgnoreCase(String username);

    Boolean existsByUsernameIgnoreCase(String username);

    List<UserEntity> deleteByUsernameIgnoreCase(String username);
}
