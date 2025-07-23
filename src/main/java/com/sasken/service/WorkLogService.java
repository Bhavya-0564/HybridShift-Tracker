package com.sasken.service;

import com.sasken.model.WorkLog;
import com.sasken.repository.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    public void saveWorkLog(WorkLog workLog) {
        workLogRepository.save(workLog);
    }

    public List<WorkLog> getAllWorkLogs() {
        return workLogRepository.findAll();
    }
   public void save(WorkLog workLog) {
        workLogRepository.save(workLog);
    }

}
