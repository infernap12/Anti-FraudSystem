package antifraud.user;

import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/auth/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserCreationResponse> createUser(@RequestBody UserCreationRequest request) {
        final UserEntity user = userService.registerUser(request.name(), request.username(), request.password());
        val response = new UserCreationResponse(user.getId(), user.getName(), user.getUsername());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserCreationResponse>> listUsers() {
        final List<UserEntity> users = userService.getAllUsers();
        List<UserCreationResponse> userDtoList = users.stream().map(UserCreationResponse::new).toList();
        return ResponseEntity.ok(userDtoList);
    }

    @Transactional
    @DeleteMapping("/user/{username}")
    public ResponseEntity<UserDeleteResponse> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok(new UserDeleteResponse(username));

    }
}
