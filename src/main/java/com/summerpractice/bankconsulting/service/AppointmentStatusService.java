package com.summerpractice.bankconsulting.service;

import com.summerpractice.bankconsulting.model.AppointmentStatus;
import com.summerpractice.bankconsulting.model.Location;
import com.summerpractice.bankconsulting.model.UpdateAppointmentStatusRequest;
import com.summerpractice.bankconsulting.repository.AppointmentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AppointmentStatusService {
    @Autowired
    private AppointmentStatusRepository appointmentStatusRepository;


    public AppointmentStatus createLocation(AppointmentStatus appointmentStatus){
        return appointmentStatusRepository.save(appointmentStatus);
    }


    public List<AppointmentStatus> findAllByStatus(String type){
       List<AppointmentStatus> statusList = appointmentStatusRepository.findAllByStatus(type);
        return statusList;
    }
    public AppointmentStatus getAppointmentStatusById(int id) {
        Optional<AppointmentStatus> appointmentStatus = appointmentStatusRepository.findById(id);
        if (appointmentStatus.isPresent()) {
            return appointmentStatus.get();
        }
        return null;
    }


    public int updateAppointmentStatus(int id, UpdateAppointmentStatusRequest updateAppointmentStatusRequest){
        Optional<AppointmentStatus> appointmentStatus1 = appointmentStatusRepository.findById(id);
        if(appointmentStatus1.isPresent()){
            AppointmentStatus originalAppointmentStatus = appointmentStatus1.get();
            if(Objects.nonNull(updateAppointmentStatusRequest.getStatusType()) && !"".equalsIgnoreCase(updateAppointmentStatusRequest.getStatusType())){
                originalAppointmentStatus.setStatusType(updateAppointmentStatusRequest.getStatusType());
                appointmentStatusRepository.save(originalAppointmentStatus);
            }
            return 0;
        }
        return -1;
    }


    public List<AppointmentStatus> getAll(){
        return appointmentStatusRepository.findAll();
    }

    public String deleteAppointmentStatusById(int id){
        if(appointmentStatusRepository.findById(id).isPresent()){
            appointmentStatusRepository.deleteById(id);
            return "Location deleted successfully";
        }
        return "No such location found";
    }
}




