package com.sasken.repository;

import com.sasken.model.ShiftSchedule;
import com.sasken.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShiftScheduleRepository extends JpaRepository<ShiftSchedule, Long> {

    // 🔄 Get all schedules of an employee
    List<ShiftSchedule> findByEmployeeId(Long employeeId);

    // 🔄 Get all schedules for a specific date
    List<ShiftSchedule> findByDate(LocalDate date);

    // ✅ Get a specific schedule by employee and date
    Optional<ShiftSchedule> findByEmployeeAndDate(Employee employee, LocalDate date);
  

}
