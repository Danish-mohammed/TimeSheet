package com.org.timesheet.repository.userrepository;

import com.org.timesheet.model.timesheet.Task;
import com.org.timesheet.model.userentity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // Eager fetching using JpaRepository method
//    User userWithRoles = findById(userId, FetchRequest.create("roles").fetch("roles"));

    // Custom query with JpaRepository (optional)
//    @Query(value = "SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = :userId",nativeQuery = true)
//    User userWithRoles = findOneByQuery(userId);
    User findByUsername(String username);
}
