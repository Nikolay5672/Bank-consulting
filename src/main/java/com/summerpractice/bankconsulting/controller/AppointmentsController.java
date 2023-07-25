package com.summerpractice.bankconsulting.controller;

import com.summerpractice.bankconsulting.email.EmailService;
import com.summerpractice.bankconsulting.model.Appointment;
import com.summerpractice.bankconsulting.model.Employee;
import com.summerpractice.bankconsulting.model.Location;
import com.summerpractice.bankconsulting.service.AppointmentService;
import com.summerpractice.bankconsulting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consulting/appointments/v1.0.0")
public class AppointmentsController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/appointments-get")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/appointments-getId/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable(value = "id") Integer appointmentId) {
        Optional<Appointment> appointment = Optional.ofNullable(appointmentService.getAppointmentById(appointmentId));
        if (appointment.isPresent()) {
            return ResponseEntity.ok().body(appointment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/appointments-save")
    public ResponseEntity<Void> createAppointment(@RequestBody Appointment appointment) {
        appointmentService.saveAppointment(appointment);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/appointments-updateId/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable(value = "id") Integer appointmentId,
                                                         @RequestBody Appointment appointmentDetails) {
        Optional<Appointment> appointment = Optional.ofNullable(appointmentService.getAppointmentById(appointmentId));
        if (appointment.isPresent()) {
            Appointment updatedAppointment = appointment.get();
            updatedAppointment.setFirstName(appointmentDetails.getFirstName());
            updatedAppointment.setLastName(appointmentDetails.getLastName());
            updatedAppointment.setEmail(appointmentDetails.getEmail());
            updatedAppointment.setPhone(appointmentDetails.getEmail());
            updatedAppointment.setDateTime(appointmentDetails.getDateTime());
            updatedAppointment.setServiceId(appointmentDetails.getServiceId());
            updatedAppointment.setLocationId(appointmentDetails.getLocationId());
            updatedAppointment.setStatusId(appointmentDetails.getStatusId());
            updatedAppointment.setEmployeeId(appointmentDetails.getEmployeeId());
            appointmentService.saveAppointment(updatedAppointment);
            return ResponseEntity.ok().body(updatedAppointment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable(value = "id") Integer appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment!=null) {
            appointmentService.deleteAppointment(appointmentId);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/appointment-cancelled/{id}/employee-id/{employee_id}")
    public void cancelAppointment(@PathVariable(value = "id") Integer appointmentId,
                                  @PathVariable("employee_id") int employeeId) {
        int cancelStatus = appointmentService.updateStatusToCancel(3, appointmentId);
        int cancelledEmployee = appointmentService.setCancelledEmployee(employeeId, appointmentId);
    }

    @PutMapping("/appointment-finished/{id}")
    public void finishAppointment(@PathVariable(value = "id") Integer appointmentId) {
        int finishStatus = appointmentService.updateStatusToCancel(5, appointmentId);
    }

    @GetMapping("/appointments-getEmployeeId/{employee_id}")
    public ResponseEntity<List<Appointment>> getAppointmentByEmployeeId(@PathVariable(value = "employee_id") Integer appointmentEmployeeId) {
        List<Appointment> appointments = appointmentService.getAppointmentByEmployeeId(appointmentEmployeeId);
        if (!appointments.isEmpty()) {
            return ResponseEntity.ok(appointments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/employee/{employeeId}")
    public ResponseEntity<String> updateAppointmentEmployeeIdAndStatus(
            @PathVariable("id") int id,
            @PathVariable("employeeId") int employeeId) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        boolean updatedEmployee = appointmentService.updateEmployeeId(id, employeeId);
        int updatedStatus = appointmentService.updateStatus(2, id);
        int updatedStatusTo3 = appointmentService.setStatusAfterDate(4,currentDateTime, id);

        if (updatedEmployee) {
            // Fetch appointment data
            Appointment appointment = appointmentService.getAppointmentById(id);
            if (appointment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
            }

            // Fetch employee data
            Employee employee = employeeService.getEmployeeById(employeeId);
            if (employee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }

            // Retrieve the necessary details from the employee and appointment objects
            String employeeName = employee.getFirstName();
            LocalDateTime appointmentDateTime = appointment.getDateTime();
            Location appointmentLocation = appointment.getLocationId();
            String locationAddress = appointmentLocation.getAddress();
            if(locationAddress==null){
                locationAddress=appointmentLocation.getUrl();
            }
            emailService.send(employee.getEmail(), buildEmail(employeeName,appointmentDateTime,locationAddress));
            emailService.send(appointment.getEmail(), buildEmail(appointment.getFirstName(),appointmentDateTime,locationAddress));

            // Perform further processing or actions with the fetched data
            // ...

            return ResponseEntity.ok("Appointment employeeId updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
        }
    }

    private String buildEmail(String name , LocalDateTime date, String location){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = date.format(formatter);
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <title>Consultation confirmed!</title>\n" +
                "</head>\n" +
                "<body style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 1.6; margin: 0; padding: 0;\">\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse; min-width: 100%; width: 100%!important; background-color: #f6f6f6;\" bgcolor=\"#f6f6f6\">\n" +
                "    <tr>\n" +
                "      <td align=\"center\" style=\"padding: 30px;\">\n" +
                "        <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse; max-width: 580px; margin: 0 auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\">\n" +
                "          <tr>\n" +
                "            <td style=\"padding: 30px; text-align: center;\">\n" +
                "              <h2 style=\"font-size: 28px; color: #F34925; margin-bottom: 20px;\">Consultation confirmed!</h2>\n" +
                "              <p style=\"font-size: 18px; color: #333;\">Hi " + name + ",</p>\n" +
                "              <p style=\"font-size: 18px; color: #333;\"></p>\n" +
                "              <p>Thank you for choosing us for your consultation. We are looking forward to meeting you!</p>\n" +
                "              <div class=\"details\">\n" +
                "               <p><strong>Consultation Details:</strong></p>\n" +
                "               <p>Date: " + formattedDate + "</p>\n" +
                "               <p>Location: " + location + "</p>\n" +
                "              </div>\n" +
                "              <p style=\"font-size: 18px; color: #333;\">See you soon!</p>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </table>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }
}