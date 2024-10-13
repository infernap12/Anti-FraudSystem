package antifraud.auth;

import antifraud.auth.user.*;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/auth/")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = {"/user", "/user/"})
    public ResponseEntity<UserCreationResponse> createUser(@RequestBody UserCreationRequest request) {
        final UserEntity user = userService.registerUser(request.name(), request.username(), request.password());
        val response = new UserCreationResponse(user.getId(), user.getName(), user.getUsername(), user.getRole());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping({"/list", "/list/"})
    public ResponseEntity<List<UserCreationResponse>> listUsers() {
        final List<UserEntity> users = userService.getAllUsers();
        List<UserCreationResponse> userDtoList = users.stream().map(UserCreationResponse::new).toList();
        return ResponseEntity.ok(userDtoList);
    }

    @PutMapping({"/role", "/role/"})
    public ResponseEntity<UserCreationResponse> modifyRole(@RequestBody UserRoleRequest request) {
        if (request.role() == UserRole.ADMINISTRATOR) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        final UserEntity updatedUserEntity = userService.modifyRole(request.username(), request.role());
        return ResponseEntity.ok(new UserCreationResponse(updatedUserEntity));
    }

    @PutMapping({"/access", "/access/"})
    public ResponseEntity<Map<String, String>> modifyLock(@RequestBody UserLockRequest request) {
        System.out.println(request.getLock());
        System.out.println(request.username());
        System.out.println(request.operation());
        userService.modifyLock(request.username(), request.getLock());

        final String status = "User " + request.username() + " " + request.operation().getPost() + "!";
        final Map<String, String> response = Map.of("status", status);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @DeleteMapping({"/user/{username}", "/user/{username}/"})
    public ResponseEntity<UserDeleteResponse> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok(new UserDeleteResponse(username));

    }
}
