package com.sasken.repository;

import com.sasken.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // ✅ Replace username with email
    Employee findByEmail(String email);
    
    // Optional: check if email already exists
    boolean existsByEmail(String email);
}
