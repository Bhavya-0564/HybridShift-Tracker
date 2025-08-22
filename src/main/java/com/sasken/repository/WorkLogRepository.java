package com.sasken.repository;

import com.sasken.model.Employee;
import com.sasken.model.WorkLog;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    boolean existsByEmployeeAndWorkDate(Employee employee, LocalDate workDate);

}
