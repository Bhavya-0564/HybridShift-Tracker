package com.sasken.repository;

import com.sasken.model.ProductivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductivityLogRepository extends JpaRepository<ProductivityLog, Long> {
    List<ProductivityLog> findByEmployeeId(Long employeeId);
}
