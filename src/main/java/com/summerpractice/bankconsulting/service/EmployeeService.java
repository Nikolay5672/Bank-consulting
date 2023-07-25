package com.summerpractice.bankconsulting.service;

import com.summerpractice.bankconsulting.model.Employee;
import com.summerpractice.bankconsulting.model.UpdateEmployeeRequest;
import com.summerpractice.bankconsulting.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAllEmployeesSorted();
    }

    public Employee getEmployeeById(int id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            return employee.get();
        }
        return null;
    }

    public int updateEmployeeById(int id, UpdateEmployeeRequest updateEmployeeRequest){
        Optional<Employee> employee1 = employeeRepository.findById(id);

        if(employee1.isPresent()){
            Employee originalEmployee = employee1.get();

            if(Objects.nonNull(updateEmployeeRequest.getFirstName()) && Objects.nonNull(updateEmployeeRequest.getLastName()) &&
            !"".equals(updateEmployeeRequest.getFirstName()) && !"".equals(updateEmployeeRequest.getLastName())){
                originalEmployee.setFirstName(updateEmployeeRequest.getFirstName());
                originalEmployee.setLastName(updateEmployeeRequest.getLastName());
            }
            if(Objects.nonNull(updateEmployeeRequest.getEmpPassword()) && !"".equals(updateEmployeeRequest.getEmpPassword())){
                originalEmployee.setEmpPassword(updateEmployeeRequest.getEmpPassword());
            }
            if(Objects.nonNull(updateEmployeeRequest.getEmail()) && !"".equals(updateEmployeeRequest.getEmail())){
                originalEmployee.setEmail(updateEmployeeRequest.getEmail());
            }
            if(Objects.nonNull(updateEmployeeRequest.getPhone()) && !"".equals(updateEmployeeRequest.getPhone())){
                originalEmployee.setPhone(updateEmployeeRequest.getPhone());
            }
            if(Objects.nonNull(updateEmployeeRequest.getRoleId())){
                originalEmployee.setRoleId(updateEmployeeRequest.getRoleId());
            }
            if(Objects.nonNull(updateEmployeeRequest.getDepId())){
                originalEmployee.setDepId(updateEmployeeRequest.getDepId());
            }
            employeeRepository.save(originalEmployee);
            return 0;
        }
        return -1;
    }

    public String deleteEmployee(int id){
        if(employeeRepository.findById(id).isPresent()){
            employeeRepository.deleteById(id);
            return "Employee deleted successfully";
        }
        return "No employee with the current ID found.";
    }

    public Employee getEmployeeByEmpPassword(String emp_password) {
        return employeeRepository.findByEmpPassword(emp_password);
    }
}
