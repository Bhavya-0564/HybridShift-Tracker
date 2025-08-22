package com.sasken.service;

import com.sasken.model.Employee;
import com.sasken.model.ProductivityLog;
import com.sasken.model.ShiftSchedule;
import com.sasken.repository.ProductivityLogRepository;
import com.sasken.repository.ShiftScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductivityLogService {

    @Autowired
    private ProductivityLogRepository logRepository;

    @Autowired
    private ShiftScheduleRepository shiftScheduleRepository;

    public List<ProductivityLog> getAllLogs() {
        return logRepository.findAll();
    }

    public ProductivityLog getLogById(Long id) {
        return logRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Productivity Log not found for ID: " + id));
    }

    public void save(ProductivityLog log) {
        Optional<ProductivityLog> existingLog = logRepository.findByEmployeeIdAndDate(
                log.getEmployee().getId(), log.getDate());

        // ✅ Set actual login time if not already set
        if (log.getActualLoginTime() == null) {
            log.setActualLoginTime(LocalDateTime.now());
        }

        // ✅ Fetch and set scheduled login time from shift schedule
        if (log.getScheduledLoginTime() == null) {
            Optional<ShiftSchedule> optionalSchedule = shiftScheduleRepository.findByEmployeeAndDate(
                    log.getEmployee(), log.getDate()
            );

            optionalSchedule.ifPresent(schedule -> {
                log.setScheduledLoginTime(LocalDateTime.of(schedule.getDate(), schedule.getScheduledLoginTime()));
            });
        }

        // ✅ Set late login flag
        boolean isLate = log.getScheduledLoginTime() != null
                && log.getActualLoginTime() != null
                && log.getActualLoginTime().isAfter(log.getScheduledLoginTime());

        log.setLateLogin(isLate);

        // ✅ Save or update
        if (existingLog.isPresent()) {
            ProductivityLog existing = existingLog.get();
            existing.setTaskSummary(log.getTaskSummary());
            existing.setHoursWorked(log.getHoursWorked());
            existing.setScheduledLoginTime(log.getScheduledLoginTime());
            existing.setActualLoginTime(log.getActualLoginTime());
            existing.setLateLogin(log.isLateLogin());
            logRepository.save(existing);
        } else {
            logRepository.save(log);
        }
    }

    public void deleteLogById(Long id) {
        if (!logRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete. Log not found for ID: " + id);
        }
        logRepository.deleteById(id);
    }

    public Map<Long, Boolean> getLateLoginMap() {
        List<ProductivityLog> logs = getAllLogs();
        Map<Long, Boolean> result = new HashMap<>();

        for (ProductivityLog log : logs) {
            LocalDateTime scheduled = log.getScheduledLoginTime();
            LocalDateTime actual = log.getActualLoginTime();

            if (scheduled != null && actual != null) {
                result.put(log.getId(), actual.isAfter(scheduled));
            } else {
                result.put(log.getId(), false);
            }
        }

        return result;
    }

    public ProductivityLog findByEmployeeAndDate(Employee employee, LocalDate date) {
        return logRepository.findByEmployeeIdAndDate(employee.getId(), date).orElse(null);
    }
}
