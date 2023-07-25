package com.summerpractice.bankconsulting.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Services serviceId;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location locationId;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private AppointmentStatus statusId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = true)
    private Employee employeeId;
}
