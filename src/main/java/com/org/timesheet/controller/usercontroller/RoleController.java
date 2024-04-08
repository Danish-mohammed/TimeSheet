package com.org.timesheet.controller.usercontroller;

import com.org.timesheet.dto.RoleDTO;
import com.org.timesheet.model.userentity.Role;
import com.org.timesheet.service.userservice.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping
    public Role addRole(@RequestBody RoleDTO roleDTO){
        return roleService.addRole(roleDTO);
    }
}
