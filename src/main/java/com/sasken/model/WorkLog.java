package com.sasken.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "work_log")  // match the DB table name
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "work_date")
    private LocalDate workDate;

    @Column(name = "work_mode") 
    private String mode;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "login_time")
private LocalTime loginTime;

    
}
