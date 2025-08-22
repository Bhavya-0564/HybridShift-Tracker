package com.sasken.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
public class ProductivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "log_date")
    private LocalDate date;

    private double hoursWorked;

    private String taskSummary;

    // ✅ New fields for late login tracking
    private LocalDateTime scheduledLoginTime;

    private LocalDateTime actualLoginTime;

    // ✅ Add this flag to mark late logins
    private boolean lateLogin;

    // === Getters and Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getTaskSummary() {
        return taskSummary;
    }

    public void setTaskSummary(String taskSummary) {
        this.taskSummary = taskSummary;
    }

    public LocalDateTime getScheduledLoginTime() {
        return scheduledLoginTime;
    }

    public void setScheduledLoginTime(LocalDateTime scheduledLoginTime) {
        this.scheduledLoginTime = scheduledLoginTime;
    }

    public LocalDateTime getActualLoginTime() {
        return actualLoginTime;
    }

    public void setActualLoginTime(LocalDateTime actualLoginTime) {
        this.actualLoginTime = actualLoginTime;
    }

    public boolean isLateLogin() {
        return lateLogin;
    }

    public void setLateLogin(boolean lateLogin) {
        this.lateLogin = lateLogin;
    }

    // ✅ Additional getters for Thymeleaf date formatting
    public Date getScheduledLoginAsDate() {
        return scheduledLoginTime != null
                ? Date.from(scheduledLoginTime.atZone(ZoneId.systemDefault()).toInstant())
                : null;
    }

    public Date getActualLoginAsDate() {
        return actualLoginTime != null
                ? Date.from(actualLoginTime.atZone(ZoneId.systemDefault()).toInstant())
                : null;
    }
}
