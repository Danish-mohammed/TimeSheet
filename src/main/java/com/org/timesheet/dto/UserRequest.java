package com.org.timesheet.dto;

import com.org.timesheet.model.userentity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password; // Consider using a password encoder for secure storage
    private String email;
    private Set<Role> roles;
}
