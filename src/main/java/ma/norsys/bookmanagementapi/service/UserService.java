package ma.norsys.bookmanagementapi.service;

import ma.norsys.bookmanagementapi.dto.requestDto.UserRequest;
import ma.norsys.bookmanagementapi.dto.responseDto.UserResponse;
import ma.norsys.bookmanagementapi.entities.User;
import ma.norsys.bookmanagementapi.exception.EntityNotFoundException;
import ma.norsys.bookmanagementapi.mapper.UserMapper;
import ma.norsys.bookmanagementapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createNewUser(UserRequest userRequest) {
        User user = UserMapper.INSTANCE.toEntity(userRequest);
        userRepository.save(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return mapUsersToUserResponses(users);
    }

    public UserResponse getUserById(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return mapUserToUserResponse(optionalUser.get());
        } else {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }

    public void updateUser(long userId, UserRequest userRequest) {
        User oldUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        oldUser.setFullName(userRequest.fullName());
        oldUser.setEmail(userRequest.email());
        oldUser.setPhone_number(userRequest.phone_number());
        this.userRepository.save(oldUser);
    }

    public void deleteUser(long userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            this.userRepository.deleteById(userId);
        } else {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
    }

    private UserResponse mapUserToUserResponse(User user) {
        return UserMapper.INSTANCE.toDto(user);
    }

    private List<UserResponse> mapUsersToUserResponses(List<User> users) {
        return users.stream()
                .map(user -> mapUserToUserResponse(user))
                .toList();
    }

}
