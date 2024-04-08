package com.org.timesheet.service.userservice;

import com.org.timesheet.dto.LoginRequest;
import com.org.timesheet.dto.UserRequest;
import com.org.timesheet.model.userentity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
        User saveUser(UserRequest user);
        ResponseEntity<?> getUserById(Long id);
        User findByUsername(String username);

        ResponseEntity<User> updateUser(Long id, UserRequest userRequest);

        ResponseEntity<Void> deleteById(Long id);

        boolean authenticateUser(LoginRequest loginRequest);
}
