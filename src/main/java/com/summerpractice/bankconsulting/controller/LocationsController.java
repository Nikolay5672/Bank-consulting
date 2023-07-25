package com.summerpractice.bankconsulting.controller;

import com.summerpractice.bankconsulting.model.Location;
import com.summerpractice.bankconsulting.model.Services;
import com.summerpractice.bankconsulting.model.UpdateLocationRequest;
import com.summerpractice.bankconsulting.model.UpdateRoleRequest;
import com.summerpractice.bankconsulting.service.LocationsService;
import com.summerpractice.bankconsulting.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consulting/locations/v1.0.0")
public class LocationsController {
    @Autowired
    private LocationsService locationsService;

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationsService.getAllLocations());
    }

    @PostMapping("/save-locations")
    public ResponseEntity<Void> saveLocations(@RequestBody Location location) {
        locationsService.saveLocation(location);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/get-location/{id}")
    public Location getALocationById(@PathVariable int id){
        return locationsService.getLocationById(id);
    }

    @PutMapping("/update-location/{id}")
    public ResponseEntity<Void> updateLocationById(@PathVariable("id") int id, @RequestBody UpdateLocationRequest locationRequest){
        int response = locationsService.updateLocationById(id,locationRequest);
        if(response<0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id) {
        int response = locationsService.deleteLocationById(id);
        if(response < 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}


