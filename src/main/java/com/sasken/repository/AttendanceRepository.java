package com.sasken.repository;

import com.sasken.model.Attendance;
import com.sasken.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Used to avoid duplicate attendance entries
    Optional<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);

    // Used to fetch attendance for today's dashboard
    List<Attendance> findByDate(LocalDate date);
   boolean existsByEmployeeAndDate(Employee employee, LocalDate date);

}
