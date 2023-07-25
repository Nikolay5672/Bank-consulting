package com.summerpractice.bankconsulting.repository;
import com.summerpractice.bankconsulting.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentStatusRepository extends JpaRepository<AppointmentStatus, Integer> {
    @Query(value = "Select * from appointment_status where status_type = :status", nativeQuery = true)
    List<AppointmentStatus> findAllByStatus(@Param("status")String status);
}
