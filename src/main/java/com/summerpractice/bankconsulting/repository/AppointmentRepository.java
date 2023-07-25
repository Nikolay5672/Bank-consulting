package com.summerpractice.bankconsulting.repository;
import com.summerpractice.bankconsulting.model.Appointment;
import com.summerpractice.bankconsulting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT * FROM appointments a " +
            "WHERE a.date_time = :dateTime AND a.service_id = :serviceId",
            nativeQuery = true)
    List<Appointment> findExistingAppointmentsByDateTimeAndServiceId(
            @Param("dateTime") LocalDateTime dateTime,
            @Param("serviceId") Long serviceId
    );
    @Query(value = "SELECT * FROM appointments WHERE id = ?1", nativeQuery = true)
    Appointment findAppointmentById(int id);

    @Query(value = "SELECT * FROM appointments WHERE employee_id = ?1", nativeQuery = true)
    List<Appointment> findAppointmentByEmployeeId(int employeeId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE appointments SET status_id=?1 WHERE date_time<?2 AND id=?3", nativeQuery = true)
    Integer updateStatusAfterDate (int statusId, LocalDateTime currentDateTime, int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE appointments SET status_id=?1 WHERE id=?2", nativeQuery = true)
    int updateStatus (int statusId, int employeeId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE appointments SET employee_id=?1 WHERE id=?2", nativeQuery = true)
    int updateEmployeeForCancelledApp (int employeeId, int appointmentId);
}
