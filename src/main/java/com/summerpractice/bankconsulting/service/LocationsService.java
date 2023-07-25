package com.summerpractice.bankconsulting.service;

import com.summerpractice.bankconsulting.model.*;
import com.summerpractice.bankconsulting.repository.LocationRepository;
import com.summerpractice.bankconsulting.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LocationsService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return (List<Location>) locationRepository.findAll();
    }

    public void saveLocation(Location location) {
        locationRepository.save(location);
    }


    public Location getLocationById(int id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            return location.get();
        }
        return null;
    }

    public int updateLocationById(int id, UpdateLocationRequest locationRequest) {
        Optional<Location> location1 = locationRepository.findById(id);

        if (location1.isPresent()) {
            Location originalLocation = location1.get();

            if (Objects.nonNull(locationRequest.getAddress()) || Objects.nonNull(locationRequest.getUrl())) {
                originalLocation.setAddress(locationRequest.getAddress());
                originalLocation.setUrl(locationRequest.getUrl());

                locationRepository.save(originalLocation);
                return 0;
            }

        }
        return -1;
    }


    public int deleteLocationById(int id){
        if(locationRepository.findById(id).isPresent()){
            locationRepository.deleteById(id);
            return 0;
        }
        return -1;
    }
}

