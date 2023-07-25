package com.summerpractice.bankconsulting.service;

import com.summerpractice.bankconsulting.model.Role;
import com.summerpractice.bankconsulting.model.Services;
import com.summerpractice.bankconsulting.model.UpdateRoleRequest;
import com.summerpractice.bankconsulting.model.UpdateServicesRequest;
import com.summerpractice.bankconsulting.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }
    public int updateRoleById(int id, UpdateRoleRequest roleRequest) {
        Optional<Role> role1 = roleRepository.findById(id);

        if (role1.isPresent()) {
            Role originalRole = role1.get();

            if (Objects.nonNull(roleRequest.getRoleType())) {
                originalRole.setRoleType(roleRequest.getRoleType());

                roleRepository.save(originalRole);
            }
            return 0;
        }
        return -1;
    }
}

