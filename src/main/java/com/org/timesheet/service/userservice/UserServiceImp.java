package com.org.timesheet.service.userservice;

import com.org.timesheet.dto.LoginRequest;
import com.org.timesheet.dto.UserRequest;
import com.org.timesheet.model.userentity.Role;
import com.org.timesheet.model.userentity.User;
import com.org.timesheet.repository.userrepository.RoleRepository;
import com.org.timesheet.repository.userrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User saveUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());

        Set<Role> persistedRoles = new HashSet<>();
        for (Role role : userRequest.getRoles()) {
            // Check if the role exists in the database
            Optional<Role> optionalRole = roleRepository.findByName(role.getName());
            if (optionalRole.isPresent()) {
                // If the role exists, add it to the set of persisted roles
                persistedRoles.add(optionalRole.get());
            } else {
                // If the role doesn't exist, create a new role entity and add it to the set
                Role newRole = new Role();
                newRole.setName(role.getName());
                persistedRoles.add(roleRepository.save(newRole));
            }
        }
        user.setRoles(persistedRoles);
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> {
            user.getRoles().size(); // Eagerly fetch roles
            System.out.println(user.getRoles().size());
            return ResponseEntity.ok(user.toString());
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public ResponseEntity<User> updateUser(Long id, UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userRequest.getUsername());
            user.setPassword(userRequest.getPassword());
            user.setEmail(userRequest.getEmail());

            Set<Role> persistedRoles = new HashSet<>();
            for (Role role : userRequest.getRoles()) {
                Optional<Role> optionalRole = roleRepository.findByName(role.getName());
                if (optionalRole.isPresent()) {
                    persistedRoles.add(optionalRole.get());
                } else {
                    Role newRole = new Role();
                    newRole.setName(role.getName());
                    persistedRoles.add(roleRepository.save(newRole));
                }
            }
            user.setRoles(persistedRoles);
            return ResponseEntity.ok(userRepository.save(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public boolean authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        return user != null && user.getPassword().equals(loginRequest.getPassword());
    }
}
