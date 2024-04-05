package ma.norsys.bookmanagementapi.controller;

import jakarta.validation.Valid;
import ma.norsys.bookmanagementapi.dto.requestDto.UserRequest;
import ma.norsys.bookmanagementapi.dto.responseDto.ApiResponse;
import ma.norsys.bookmanagementapi.dto.responseDto.UserResponse;
import ma.norsys.bookmanagementapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            userService.createNewUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(200,"New user was created successfully"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(400,"Error adding new User"));
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long userId) {
        UserResponse user = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable long userId, @Valid @RequestBody UserRequest userRequest) {
        try {
            userService.updateUser(userId, userRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(200,"User updated successfully"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(400,"Error updating User"));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(200,"User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(400,"Error deleting user: " + e.getMessage()));
        }
    }
}
