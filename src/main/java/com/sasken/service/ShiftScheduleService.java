package com.sasken.service;

import com.sasken.model.Employee;
import com.sasken.model.ShiftSchedule;
import com.sasken.repository.ShiftScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShiftScheduleService {

    @Autowired
    private ShiftScheduleRepository shiftScheduleRepository;

    // ✅ Save a new shift schedule
    public void saveSchedule(ShiftSchedule schedule) {
        shiftScheduleRepository.save(schedule);
    }

    // ✅ Retrieve all schedules
    public List<ShiftSchedule> getAllSchedules() {
        return shiftScheduleRepository.findAll();
    }

    // ✅ Retrieve today's schedules
    public List<ShiftSchedule> getTodaySchedules() {
        LocalDate today = LocalDate.now();
        return shiftScheduleRepository.findByDate(today);
    }

    // ✅ Get a specific schedule for employee and date (with Optional handled properly)
    public ShiftSchedule getScheduleByEmployeeAndDate(Employee employee, LocalDate date) {
        return shiftScheduleRepository.findByEmployeeAndDate(employee, date).orElse(null);
    }
}
