package com.sasken.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USERS") // ✅ Avoids reserved keyword issue
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String role; // e.g., "EMPLOYEE" or "MANAGER"
}
