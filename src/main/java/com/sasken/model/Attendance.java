package com.sasken.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "attendance_date")
    private LocalDate date;

    @Column(name = "actual_mode")
    private String actualMode; // remote / office / hybrid

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
