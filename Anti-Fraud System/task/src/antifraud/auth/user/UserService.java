package antifraud.auth.user;

import antifraud.auth.UserRole;
import lombok.val;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return getUser(username).asUserDetails();
    }

    public UserEntity registerUser(String name, String username, String password) {
        if (repo.existsByUsernameIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already registered");
        }
        val user = new UserEntity(name, username, password);
        val returnedUser = repo.save(user);

        if (returnedUser.getId() == 1) {
            returnedUser.setRole(UserRole.ADMINISTRATOR);
            returnedUser.setLocked(false);
        }
        return repo.save(returnedUser);
    }

    public UserEntity getUser(String username) {
        return repo
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<UserEntity> getAllUsers() {
        return getAllUsers(Sort.by(Sort.Order.asc("id")));
    }

    public List<UserEntity> getAllUsers(Sort sort) {
        return repo.findAll(sort);
    }


    public void deleteUser(UserEntity user) {
        repo.delete(user);
    }

    public void deleteUser(String username) {
        val user = getUser(username);
        deleteUser(user);
    }

    public UserEntity modifyRole(String username, UserRole role) {
        val user = getUser(username);
        return modifyRole(user, role);
    }

    public UserEntity modifyRole(UserEntity user, UserRole role) {
        if (user.getRole().equals(role)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already assigned");
        }

        user.setRole(role);

        return repo.save(user);
    }

    public void modifyLock(String username, boolean lock) {
        modifyLock(getUser(username), lock);
    }

    public void modifyLock(UserEntity user, boolean lock) {
        if (user.getRole() == UserRole.ADMINISTRATOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot lock administrator account");
        }
        user.setLocked(lock);
        repo.save(user);
    }
}