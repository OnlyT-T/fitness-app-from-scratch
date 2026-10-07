package com.progressfit.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.progressfit.backend.entity.User;
import com.progressfit.backend.exception.ResourceNotFoundException;
import com.progressfit.backend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // POST
    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                "Email already exists: " + user.getEmail()
            );
        }
        
        return userRepository.save(user);
    }

    // GET all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET one user with id
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // UPDATE
    public User updateUser(Long id, User updatedUser) {

        User existingUser = getUserById(id);

        existingUser.setDisplayName(updatedUser.getDisplayName());
        existingUser.setEmail(updatedUser.getEmail());

        return userRepository.save(existingUser);
    }

    // DELETE
    public void deleteUserById(Long id) {

        User existingUser = getUserById(id);

        userRepository.delete(existingUser);
    }
}
