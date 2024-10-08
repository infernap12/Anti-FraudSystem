package antifraud.user;

import lombok.val;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws org.springframework.security.core.userdetails.UsernameNotFoundException {
        UserEntity user = repo
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.asUserDetails();
    }

    public UserEntity registerUser(String name, String username, String password) {
        val user = new UserEntity(name, username, password);
        if (repo.existsByUsernameIgnoreCase(username)) {
            throw new UsernameInUseException("User already registered");
        }
        return repo.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return getAllUsers(Sort.by(Sort.Order.asc("id")));
    }

    public List<UserEntity> getAllUsers(Sort sort) {
        return repo.findAll(sort);
    }

    public List<UserEntity> deleteUser(String username) {
        if (!repo.existsByUsernameIgnoreCase(username)) {
            throw new UsernameNotFoundException("User not found");
        }
        return repo.deleteByUsernameIgnoreCase(username);
    }
}