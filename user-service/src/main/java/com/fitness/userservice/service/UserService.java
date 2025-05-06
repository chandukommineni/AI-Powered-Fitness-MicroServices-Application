package com.fitness.userservice.service;


import com.fitness.userservice.dto.UserRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.exceptions.UserAlreadyExistsException;
import com.fitness.userservice.exceptions.UserNotFoundException;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public UserResponse getUserDetails(String userId) {
        User user=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User with id "+userId+" Not Available"));
       return responseMapper(user);
    }

    public UserResponse registerUser(UserRequest userRequest) {
        if(userRepository.existsByEmail(userRequest.getEmail())){
            User existingUser=userRepository.findByEmail(userRequest.getEmail());
           return responseMapper(existingUser);
        }
        User user=userRepository.save(requestMapper(userRequest));
        return responseMapper(user);

    }

    public Boolean checkUserExists(String keycloakId){
        return userRepository.existsByKeycloakId(keycloakId);
    }

    public UserResponse responseMapper(User user){
        UserResponse userResponse=new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setId(user.getId());
        userResponse.setRole(user.getRole());
        userResponse.setPassword(user.getPassword());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        userResponse.setKeycloakId(user.getKeycloakId());
        return userResponse;
    }

    public User requestMapper(UserRequest userRequest){
        User user=new User();
        user.setKeycloakId(userRequest.getKeycloakId());
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(userRequest.getPassword());
        return user;
    }
}
