package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.RoleRequest;
import com.devteria.identity_service.dto.response.PermissionResponse;
import com.devteria.identity_service.dto.response.RoleResponse;
import com.devteria.identity_service.entity.Permission;
import com.devteria.identity_service.entity.Role;
import com.devteria.identity_service.repository.PermissionRepository;
import com.devteria.identity_service.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RoleResponse create(RoleRequest request) {
        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        Role role = modelMapper.map(request, Role.class);
        role.setPermissions(permissions);
        Role savedRole = roleRepository.save(role);
        RoleResponse result = modelMapper.map(savedRole, RoleResponse.class);
//        result.setPermissions(savedRole.getPermissions().stream().map(item -> modelMapper.map(item, PermissionResponse.class)).toList());
        return result;
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(item -> modelMapper.map(item, RoleResponse.class)).toList();
    }

    public void deleteByName(String roleName) {
        roleRepository.deleteById(roleName);
    }
}
