package com.sasken.service;

import com.sasken.model.ProductivityLog;
import com.sasken.repository.ProductivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductivityLogService {

    @Autowired
    private ProductivityLogRepository logRepository;

    // Retrieve all productivity logs
    public List<ProductivityLog> getAllLogs() {
        return logRepository.findAll();
    }

    // Retrieve a specific log by ID
    public ProductivityLog getLogById(Long id) {
        return logRepository.findById(id).orElseThrow(() -> 
            new IllegalArgumentException("Productivity Log not found for ID: " + id));
    }

    // Save or update a productivity log
    public void save(ProductivityLog log) {
        logRepository.save(log);
    }

    // Delete a log by ID
    public void deleteLogById(Long id) {
        if (!logRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete. Log not found for ID: " + id);
        }
        logRepository.deleteById(id);
    }
}
