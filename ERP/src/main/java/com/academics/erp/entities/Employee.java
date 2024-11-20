package com.academics.erp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
@Entity
@Data
@Builder
public class Employee implements UserDetails {

    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int employee_id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String photograph_path;

    @ManyToOne
    @JoinColumn(name = "department", referencedColumnName = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
