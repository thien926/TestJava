package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.RoleRequest;
import com.devteria.identity_service.dto.response.RoleResponse;
import com.devteria.identity_service.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Slf4j
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        try {
            return ApiResponse.<RoleResponse>builder()
                    .code(201)
                    .message(null)
                    .result(roleService.create(roleRequest))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(200)
                .message(null)
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{roleName}")
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<Boolean> deleteRole(@PathVariable String roleName) {
        try {
            roleService.deleteByName(roleName);
            return ApiResponse.<Boolean>builder()
                    .code(204)
                    .message(null)
                    .result(true)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
