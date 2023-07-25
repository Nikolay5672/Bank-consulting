package com.summerpractice.bankconsulting.controller;

import com.summerpractice.bankconsulting.model.Role;
import com.summerpractice.bankconsulting.model.UpdateRoleRequest;
import com.summerpractice.bankconsulting.model.UpdateServicesRequest;
import com.summerpractice.bankconsulting.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/consulting/role/v1.0.0")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/role")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping("/save-roles")
    public ResponseEntity<Void> saveRoles(@RequestBody Role role) {
        roleService.saveRole(role);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update-role/{id}")
    public ResponseEntity<Void> updateRoleById(@PathVariable("id") int id, @RequestBody UpdateRoleRequest updateRoleRequest){
        int response = roleService.updateRoleById(id,updateRoleRequest);
        if(response<0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();

}
}