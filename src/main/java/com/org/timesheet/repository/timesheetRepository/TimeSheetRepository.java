package com.org.timesheet.repository.timesheetRepository;

import com.org.timesheet.model.timesheet.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSheetRepository extends JpaRepository<Task,Long> {
    @Query(value = "select * from task where user_id = :userId",nativeQuery = true)
    List<Task> findByUserId(long userId);
}
