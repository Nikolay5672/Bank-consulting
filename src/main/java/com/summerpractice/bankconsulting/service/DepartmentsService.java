package com.summerpractice.bankconsulting.service;

import com.summerpractice.bankconsulting.model.*;
import com.summerpractice.bankconsulting.repository.DepartmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartmentsService {
    @Autowired
    private DepartmentsRepository departmentsRepository;

    public List<Departments> getAllDepartments() {
        return (List<Departments>) departmentsRepository.findAll();
    }

    public void saveDepartment(Departments departments) {
        departmentsRepository.save(departments);
    }

    public Departments getDepartmentById(Integer id){
        Optional<Departments> departments = departmentsRepository.findById(id);
        if(departments.isPresent()){
            return departments.get();
        }
        return null;
    }

    public int deleteService(int id){
        if(departmentsRepository.findById(id).isPresent()){
            departmentsRepository.deleteById(id);
            return 0;
        }
        return -1;
    }

    public int updateDepartmentById(int id, UpdateDepartmentRequest updateDepartmentRequest) {
        Optional<Departments> departments = departmentsRepository.findById(id);
        if (departments.isPresent()) {
            Departments originalDepartment = departments.get();
            if (Objects.nonNull(updateDepartmentRequest.getName())) {
                originalDepartment.setDepartmentName(updateDepartmentRequest.getName());
                departmentsRepository.save(originalDepartment);
            }
            return 0;
        }
        return -1;
    }
}