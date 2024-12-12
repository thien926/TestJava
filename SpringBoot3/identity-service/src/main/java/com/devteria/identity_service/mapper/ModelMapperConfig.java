package com.devteria.identity_service.mapper;

import com.devteria.identity_service.dto.response.PermissionResponse;
import com.devteria.identity_service.dto.response.RoleResponse;
import com.devteria.identity_service.entity.Permission;
import com.devteria.identity_service.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Cấu hình ModelMapper với typeMap cho ánh xạ giữa Role và RoleResponse
        modelMapper.typeMap(Role.class, RoleResponse.class)
                .addMappings(mapper -> {
                    // Custom ánh xạ cho permissions
                    mapper.using(ctx -> {
                        // Chuyển đổi từ List<Permission> thành List<PermissionResponse>
                        List<Permission> permissions = (List<Permission>) ctx.getSource();
                        return permissions.stream()
                                .map(permission -> modelMapper.map(permission, PermissionResponse.class))  // Ánh xạ từng phần tử
                                .collect(Collectors.toList());
                    }).map(Role::getPermissions, RoleResponse::setPermissions);  // Chuyển quyền
                });


        return modelMapper;
    }
}