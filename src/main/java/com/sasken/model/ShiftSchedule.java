package com.sasken.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "shift_schedule")
public class ShiftSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate date;

    @Column(name = "planned_mode", nullable = false)
    private String plannedMode; // Remote / On-site / Hybrid
}
