package com.sasken.repository;

import com.sasken.model.ShiftSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShiftScheduleRepository extends JpaRepository<ShiftSchedule, Long> {

    // Existing method
    List<ShiftSchedule> findByEmployeeId(Long employeeId);

    // ➕ Add this method to support today's shifts
    List<ShiftSchedule> findByDate(LocalDate date);
}
