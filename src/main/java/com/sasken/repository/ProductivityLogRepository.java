package com.sasken.repository;

import com.sasken.model.ProductivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductivityLogRepository extends JpaRepository<ProductivityLog, Long> {

    // ✅ Get logs by employee
    List<ProductivityLog> findByEmployeeId(Long employeeId);

    // ✅ Get logs by date
    List<ProductivityLog> findByDate(LocalDate date);

    // ✅ Updated: Use Optional for safe null handling
    Optional<ProductivityLog> findByEmployeeIdAndDate(Long employeeId, LocalDate date);

    // ✅ Get logs for multiple employees on a date (for manager's dashboard)
    List<ProductivityLog> findByEmployeeIdInAndDate(List<Long> employeeIds, LocalDate date);
}
