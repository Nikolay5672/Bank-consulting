package com.summerpractice.bankconsulting.controller;

import com.summerpractice.bankconsulting.model.*;
import com.summerpractice.bankconsulting.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consulting/services/v1.0.0")
public class ServicesController {
    @Autowired
    private ServicesService servicesService;

    @GetMapping("/services")
    public ResponseEntity<List<Services>> getAllServices() {
        return ResponseEntity.ok(servicesService.getAllServices());
    }

    @PostMapping("/save-services")
    public ResponseEntity<Void> saveServices(@RequestBody Services services) {
        servicesService.saveService(services);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Integer> deleteService(@PathVariable("id") int id) {
        return ResponseEntity.ok(servicesService.deleteService(id));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<Services> updateServicePartially(
            @PathVariable(value = "id") int id, @RequestBody Services newService) {
        Services service = servicesService.getServiceById(id);
        service.setType(newService.getType());
        service.setDepartmentId(newService.getDepartmentId());
        return ResponseEntity.ok(service);
    }

    @PutMapping("/update-service/{id}")
    public ResponseEntity<Void> updateServiceById(@PathVariable("id") int id, @RequestBody UpdateServicesRequest updateServicesRequest) {
        int response = servicesService.updateServiceById(id, updateServicesRequest);
        if(response < 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}



