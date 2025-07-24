package com.sasken.controller;

import com.sasken.model.ShiftSchedule;
import com.sasken.service.ShiftScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class CalendarController {

    @Autowired
    private ShiftScheduleService scheduleService;

    @GetMapping("/calendar")
    public String showCalendar(Model model) {
        List<ShiftSchedule> schedules = scheduleService.getAllSchedules();
        model.addAttribute("schedules", schedules);
        return "calendar";
    }
}
