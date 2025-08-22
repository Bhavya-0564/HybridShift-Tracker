package com.sasken.controller;

import com.sasken.model.Employee;
import com.sasken.model.ProductivityLog;
import com.sasken.model.ShiftSchedule;
import com.sasken.model.WorkLog;
import com.sasken.service.EmployeeService;
import com.sasken.service.ProductivityLogService;
import com.sasken.service.ShiftScheduleService;
import com.sasken.service.WorkLogService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WorkLogController {

    @Autowired
    private WorkLogService workLogService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ShiftScheduleService shiftScheduleService;

    @Autowired
    private ProductivityLogService productivityLogService;

    @GetMapping("/log-work")
    public String showWorkLogForm(Model model) {
        model.addAttribute("workLog", new WorkLog());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "work-log-form";
    }

    @PostMapping("/log-work")
    public String logWork(@ModelAttribute WorkLog workLog) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        workLog.setWorkDate(today);

        workLog.setLoginTime(now);
        workLogService.save(workLog);

        // Automatically create/update productivity log
        Employee emp = workLog.getEmployee();
       ShiftSchedule schedule = shiftScheduleService.getScheduleByEmployeeAndDate(emp, today);


        if (schedule != null) {
            LocalDateTime scheduledLoginTime = LocalDateTime.of(today, schedule.getScheduledLoginTime());

            LocalDateTime actualLoginTime = LocalDateTime.of(today, now);

            boolean isLate = actualLoginTime.isAfter(scheduledLoginTime);

            ProductivityLog log = productivityLogService.findByEmployeeAndDate(emp, today);
            if (log == null) {
                log = new ProductivityLog();
                log.setEmployee(emp);
                log.setDate(today);
            }

            log.setScheduledLoginTime(scheduledLoginTime);
            log.setActualLoginTime(actualLoginTime);
            log.setLateLogin(isLate);

            productivityLogService.save(log);
        }

        return "redirect:/log-work";
    }

    @GetMapping("/work-logs")
    public String viewLogs(Model model) {
        model.addAttribute("logs", workLogService.getAllWorkLogs());
        return "work-log-list";
    }
}
