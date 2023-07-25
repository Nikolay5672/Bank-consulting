package com.summerpractice.bankconsulting.repository;

import com.summerpractice.bankconsulting.model.AppointmentStatus;
import com.summerpractice.bankconsulting.model.Employee;
import com.summerpractice.bankconsulting.model.Location;
import com.summerpractice.bankconsulting.model.Services;
import org.apache.catalina.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query
            (value = "SELECT * FROM employees WHERE emp_password = ?1",
                    nativeQuery = true)
    Employee findByEmpPassword(String emp_password);

    @Query
            (value = "SELECT * FROM employees ORDER BY role_id",
                    nativeQuery = true)
    List<Employee> findAllEmployeesSorted();

    @Query(value = "SELECT e.* FROM employees e " +
            "INNER JOIN departments d ON e.dep_id = d.id " +
            "INNER JOIN services s ON s.dep_id = d.id " +
            "WHERE e.id NOT IN (" +
            "   SELECT a.employee_id FROM appointments a " +
            "   WHERE a.date_time = :dateTime " +
            "   AND a.location_id = :locationId " +
            "   AND a.service_id = :serviceId" +
            ")",nativeQuery = true)
    List<Employee> findAvailableEmployees(
            @Param("dateTime") LocalDateTime dateTime,
            @Param("locationId") Location locationId,
            @Param("serviceId") Services serviceId);



}
