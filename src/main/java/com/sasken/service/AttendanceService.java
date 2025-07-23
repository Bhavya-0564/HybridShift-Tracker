package com.sasken.service;

import com.sasken.model.Attendance;
import com.sasken.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository repository;

    public void save(Attendance attendance) {
        repository.save(attendance);
    }

    public List<Attendance> getAll() {
        return repository.findAll();
    }
}
