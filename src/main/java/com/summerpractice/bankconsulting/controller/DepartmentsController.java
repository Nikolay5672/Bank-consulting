package com.summerpractice.bankconsulting.controller;

import com.summerpractice.bankconsulting.model.Departments;
import com.summerpractice.bankconsulting.model.Employee;
import com.summerpractice.bankconsulting.model.Services;
import com.summerpractice.bankconsulting.model.UpdateDepartmentRequest;
import com.summerpractice.bankconsulting.service.DepartmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consulting/departments/v1.0.0")
public class DepartmentsController {
    @Autowired
    private DepartmentsService departmentsService;

    @GetMapping("/get")
    public ResponseEntity<List<Departments>> getAllDepartments() {
        return ResponseEntity.ok(departmentsService.getAllDepartments());
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveDepartments(@RequestBody Departments departments) {
        departmentsService.saveDepartment(departments);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Integer> deleteDepartment(@PathVariable("id") int id) {
        return ResponseEntity.ok(departmentsService.deleteService(id));
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<Departments> updateDepartmentPartially(
            @PathVariable(value = "id") int id, @RequestBody Departments newDepartments)
    {
        Departments department = departmentsService.getDepartmentById(id);
        department.setDepartmentName(newDepartments.getDepartmentName());
        return ResponseEntity.ok(department);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateDepartmentById(@PathVariable("id") int id, @RequestBody UpdateDepartmentRequest updateDepartmentRequest) {
        int response = departmentsService.updateDepartmentById(id, updateDepartmentRequest);
        if(response < 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
