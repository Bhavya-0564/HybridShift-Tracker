package com.sasken.service;

import com.sasken.model.Attendance;
import com.sasken.model.Employee;
import com.sasken.repository.AttendanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository repository;

    // Save attendance
    public void save(Attendance attendance) {
        repository.save(attendance);
    }

    // Get all attendance records
    public List<Attendance> getAll() {
        return repository.findAll();
    }

    // Get attendance for a specific date
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    // Check if attendance already exists for employee on a given date
    public Optional<Attendance> getByEmployeeAndDate(Employee employee, LocalDate date) {
        return repository.findByEmployeeAndDate(employee, date);
    }
    public List<Attendance> getByDate(LocalDate date) {
    return repository.findByDate(date);
}

}
