package com.sasken.service;

import com.sasken.model.WorkLog;
import com.sasken.model.Attendance;
import com.sasken.model.Employee;
import com.sasken.repository.WorkLogRepository;
import com.sasken.repository.AttendanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    public void save(WorkLog workLog) {
        // Save the work log
        workLogRepository.save(workLog);

        // Auto-save attendance if not already present
        Employee employee = workLog.getEmployee();
        LocalDate date = workLog.getWorkDate();


        boolean attendanceExists = attendanceRepository.findByEmployeeAndDate(employee, date).isPresent();

        if (!attendanceExists) {
            Attendance attendance = new Attendance();
            attendance.setEmployee(employee);
            attendance.setDate(date);
            attendance.setActualMode(workLog.getMode()); // Set from WorkLog mode

            attendanceRepository.save(attendance);
        }
    }

    public List<WorkLog> getAllWorkLogs() {
        return workLogRepository.findAll();
    }
}
