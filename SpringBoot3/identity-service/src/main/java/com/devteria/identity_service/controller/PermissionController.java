package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.ApiResponse;
import com.devteria.identity_service.dto.request.PermissionRequest;
import com.devteria.identity_service.dto.response.PermissionResponse;
import com.devteria.identity_service.service.PermissionService;
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
@RequestMapping("/permissions")
@Slf4j
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        try {
            return ApiResponse.<PermissionResponse>builder()
                    .code(201)
                    .message(null)
                    .result(permissionService.create(permissionRequest))
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(200)
                .message(null)
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permissionName}")
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<Boolean> deletePermission(@PathVariable String permissionName) {
        try {
            permissionService.deleteByName(permissionName);
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
