package com.devteria.identity_service.service;

import com.devteria.identity_service.dto.request.PermissionRequest;
import com.devteria.identity_service.dto.response.PermissionResponse;
import com.devteria.identity_service.entity.Permission;
import com.devteria.identity_service.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = modelMapper.map(request, Permission.class);
        Permission savedPermission = permissionRepository.save(permission);
        return modelMapper.map(savedPermission, PermissionResponse.class);
    }

    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll().stream().map(item -> modelMapper.map(item, PermissionResponse.class)).toList();
    }

    public void deleteByName(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }
}
