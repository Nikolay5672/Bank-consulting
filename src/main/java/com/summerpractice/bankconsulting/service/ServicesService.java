package com.summerpractice.bankconsulting.service;

import com.summerpractice.bankconsulting.model.Departments;
import com.summerpractice.bankconsulting.model.Services;
import com.summerpractice.bankconsulting.model.UpdateDepartmentRequest;
import com.summerpractice.bankconsulting.model.UpdateServicesRequest;
import com.summerpractice.bankconsulting.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServicesService {
    @Autowired
    private ServiceRepository serviceRepository;

    public List<Services> getAllServices() {
        return (List<Services>) serviceRepository.findAll();
    }

    public void saveService(Services services) {
        serviceRepository.save(services);
    }

    public int deleteService(int id) {
        if (serviceRepository.findById(id).isPresent()) {
            serviceRepository.deleteById(id);
            return 0;
        }
        return -1;
    }

    public Services getServiceById(Integer id) {
        Optional<Services> services = serviceRepository.findById(id);
        if (services.isPresent()) {
            return services.get();
        }
        return null;
    }

    public int updateServiceById(int id, UpdateServicesRequest servicesRequest) {
        Optional<Services> services1 = serviceRepository.findById(id);

        if (services1.isPresent()) {
            Services originalService = services1.get();
            if (Objects.nonNull(servicesRequest.getDepId()) || Objects.nonNull(servicesRequest.getName())) {
                originalService.setDepartmentId(servicesRequest.getDepId());
                originalService.setType(servicesRequest.getName());
                serviceRepository.save(originalService);
                return 0;
            }
        }
        return -1;
    }
}