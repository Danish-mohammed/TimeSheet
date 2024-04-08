package com.org.timesheet.model.userentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.org.timesheet.dto.RoleDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties
    private Set<User> users = new HashSet<>();
    public Role(RoleDTO roleDTO) {
        this.name = roleDTO.name;
    }
}
