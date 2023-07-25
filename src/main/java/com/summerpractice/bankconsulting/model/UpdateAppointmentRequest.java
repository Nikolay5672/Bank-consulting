package com.summerpractice.bankconsulting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDateTime dateTime;

    private Services serviceId;

    private Location locationId;

    private AppointmentStatus statusId;

    private Employee employeeId;
}
