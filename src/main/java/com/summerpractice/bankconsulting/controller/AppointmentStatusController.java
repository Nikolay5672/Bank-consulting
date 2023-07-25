package com.summerpractice.bankconsulting.controller;

import com.summerpractice.bankconsulting.model.AppointmentStatus;
import com.summerpractice.bankconsulting.model.Location;
import com.summerpractice.bankconsulting.model.UpdateAppointmentStatusRequest;
import com.summerpractice.bankconsulting.model.UpdateEmployeeRequest;
import com.summerpractice.bankconsulting.service.AppointmentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointment-status")
public class AppointmentStatusController {
    @Autowired
    private AppointmentStatusService appointmentStatusService;

    @Autowired
    public AppointmentStatusController(AppointmentStatusService appointmentStatusService) {
        this.appointmentStatusService = appointmentStatusService;
    }

    @PostMapping("/create-status")
    public ResponseEntity<AppointmentStatus> create(@RequestBody AppointmentStatus appointmentStatus){
        AppointmentStatus createdStatus = appointmentStatusService.createLocation(appointmentStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStatus);
    }

    @GetMapping("/getAllStatus")
    public List<AppointmentStatus> getAll(){
        return appointmentStatusService.getAll();
    }

    @GetMapping("/type")
    public List<AppointmentStatus> getAllByStatus(@RequestParam("status")String status){
        return appointmentStatusService.findAllByStatus(status);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") int id, UpdateAppointmentStatusRequest updateAppointmentStatusRequest){
       int response = appointmentStatusService.updateAppointmentStatus(id, updateAppointmentStatusRequest);
       if(response < 0){
            return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok().build();
    }


    @GetMapping("/get-status/{id}")
    public AppointmentStatus getALocationById(@PathVariable int id){
        return appointmentStatusService.getAppointmentStatusById(id);
    }

    @DeleteMapping("/status-id")
    public ResponseEntity<Void> delete(@PathVariable("id") int id){
        appointmentStatusService.deleteAppointmentStatusById(id);
        return ResponseEntity.noContent().build();
    }
}
