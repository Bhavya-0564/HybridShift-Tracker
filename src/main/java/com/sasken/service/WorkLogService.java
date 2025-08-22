package com.sasken.service;

import com.sasken.model.*;
import com.sasken.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ShiftScheduleRepository shiftScheduleRepository;

    @Autowired
    private ProductivityLogRepository productivityLogRepository;

    public void save(WorkLog workLog) {
        workLogRepository.save(workLog);

        Employee employee = workLog.getEmployee();
        LocalDate date = workLog.getWorkDate();

        // ✅ Auto-save attendance if not already present
        boolean attendanceExists = attendanceRepository.existsByEmployeeAndDate(employee, date);
        if (!attendanceExists) {
            Attendance attendance = new Attendance();
            attendance.setEmployee(employee);
            attendance.setDate(date);
            attendance.setActualMode(workLog.getMode());
            attendanceRepository.save(attendance);
        }

        // ✅ Auto-save productivity log if not already present
        Optional<ProductivityLog> optionalLog = productivityLogRepository.findByEmployeeIdAndDate(employee.getId(), date);
        if (optionalLog.isEmpty()) {
            ProductivityLog productivityLog = new ProductivityLog();
            productivityLog.setEmployee(employee);
            productivityLog.setDate(date);
            productivityLog.setActualLoginTime(LocalDateTime.of(date, workLog.getLoginTime()));

            // Lookup scheduled login time
            Optional<ShiftSchedule> scheduleOpt = shiftScheduleRepository.findByEmployeeAndDate(employee, date);
            scheduleOpt.ifPresent(schedule ->
                productivityLog.setScheduledLoginTime(LocalDateTime.of(date, schedule.getScheduledLoginTime()))
            );

            productivityLogRepository.save(productivityLog);
        }
    }

    public List<WorkLog> getAllWorkLogs() {
        return workLogRepository.findAll();
    }

    // ✅ Utility method to check if an employee was late
    public boolean isLateLogin(LocalTime scheduled, LocalTime actual) {
        return scheduled != null && actual != null && actual.isAfter(scheduled.plusMinutes(5)); // 5-min grace
    }

    // ✅ Map WorkLog IDs to late status
    public Map<Long, Boolean> getLateStatusMap(List<WorkLog> logs) {
        Map<Long, Boolean> lateMap = new HashMap<>();

        for (WorkLog log : logs) {
            ShiftSchedule schedule = shiftScheduleRepository
                .findByEmployeeAndDate(log.getEmployee(), log.getWorkDate())
                .orElse(null);

            if (schedule != null && log.getLoginTime() != null) {
                boolean isLate = isLateLogin(schedule.getScheduledLoginTime(), log.getLoginTime());
                lateMap.put(log.getId(), isLate);
            } else {
                lateMap.put(log.getId(), false); // Default to not late
            }
        }

        return lateMap;
    }
}
