package com.org.timesheet.service.userservice;

import com.org.timesheet.dto.RoleDTO;
import com.org.timesheet.model.userentity.Role;
import com.org.timesheet.repository.userrepository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role addRole(RoleDTO roleDTO){
        Role role_Data = new Role(roleDTO);
        return roleRepository.save(role_Data);
    }
}
