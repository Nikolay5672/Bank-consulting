package com.summerpractice.bankconsulting.controller;

import com.summerpractice.bankconsulting.model.Employee;
import com.summerpractice.bankconsulting.model.UpdateEmployeeRequest;
import com.summerpractice.bankconsulting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/consulting/employees/v1.0.0")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/get-id/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable("id") int id, @RequestBody UpdateEmployeeRequest updateEmployeeRequest) {
        int response = employeeService.updateEmployeeById(id, updateEmployeeRequest);
        if(response < 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") int id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }

    @GetMapping("/get/{empPassword}")
    public ResponseEntity<Employee> getEmployeeByNumber(@PathVariable("empPassword") String empPassword) {
        Employee employee = employeeService.getEmployeeByEmpPassword(empPassword);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
