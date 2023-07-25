package com.summerpractice.bankconsulting.service;

import com.summerpractice.bankconsulting.model.Appointment;
import com.summerpractice.bankconsulting.model.AppointmentStatus;
import com.summerpractice.bankconsulting.model.Employee;
import com.summerpractice.bankconsulting.model.Services;
import com.summerpractice.bankconsulting.repository.AppointmentRepository;
import com.summerpractice.bankconsulting.repository.AppointmentStatusRepository;
import com.summerpractice.bankconsulting.repository.EmployeeRepository;
import com.summerpractice.bankconsulting.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validation.Validation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@Service
public class AppointmentService {
    @Autowired
    private ServiceRepository appointmentTypeRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AppointmentStatusRepository appointmentStatusRepository;

    public List<Services> getAllAppointmentTypes() {

        return appointmentTypeRepository.findAll();
    }


    public void saveAppointment(Appointment appointment) {
        LocalDateTime appointmentTime = appointment.getDateTime();
        Services service = appointment.getServiceId();

        List<Employee> availableEmployees =
                employeeRepository.findAvailableEmployees(appointmentTime, appointment.getLocationId(), service);

        if (!availableEmployees.isEmpty()) {
            AppointmentStatus pendingStatus = appointmentStatusRepository.findById(1).orElseThrow(() -> new RuntimeException("Pending status not found!"));
            appointment.setStatusId(pendingStatus);

            appointmentRepository.save(appointment);
        }
        else{
            throw new RuntimeException("No available employees for that hour and date at that location");
        }
    }
        public List<Appointment> getAllAppointments () {
            return (List<Appointment>) appointmentRepository.findAll();
        }

        public Appointment getAppointmentById ( int id){
            Appointment appointment = appointmentRepository.findAppointmentById(id);
            return appointment;
        }
        public void updateAppointment (Appointment appointment){
        if (appointmentRepository.existsById(appointment.getId())) {
                appointmentRepository.save(appointment);
        }
    }

    public void deleteAppointment(Integer id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentByEmployeeId(int employeeId) {
        return appointmentRepository.findAppointmentByEmployeeId(employeeId);
    }

    public int updateStatusToCancel(int statusId, int appointmentId){
        return appointmentRepository.updateStatus(statusId, appointmentId);
    }

    public int setCancelledEmployee(int employeeId, int appointmentId){
        return appointmentRepository.updateEmployeeForCancelledApp(employeeId, appointmentId);
    }


    public Integer updateStatus(int statusId, int appointmentId){
        return appointmentRepository.updateStatus(statusId, appointmentId);
    }
        public int setStatusAfterDate ( int statusId, LocalDateTime currentDateTime,int id){
            return appointmentRepository.updateStatusAfterDate(statusId, currentDateTime, id);
        }

        public boolean updateEmployeeId ( int appointmentId, Integer employeeId){
            Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);

            if (optionalAppointment.isPresent()) {
                Appointment appointment = optionalAppointment.get();

                if (employeeId != null) {
                    Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
                    if (optionalEmployee.isPresent()) {
                        Employee employee = optionalEmployee.get();
                        appointment.setEmployeeId(employee);
                    } else {
                        // Handle the case where the Employee with the given employeeId doesn't exist
                        return false;
                    }
                } else {
                    appointment.setEmployeeId(null); // If employeeId is null, set the appointment's employeeId to null
                }

                appointmentRepository.save(appointment);
                return true;
            } else {
                return false;
            }
        }
    }
