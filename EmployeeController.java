package com.example.employeeapi.controller;

import com.example.employeeapi.dto.EmployeeRequestDTO;
import com.example.employeeapi.dto.EmployeeResponseDTO;
import com.example.employeeapi.response.ApiResponse;
import com.example.employeeapi.service.EmployeeService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")   // ✅ Base URL
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // ➕ CREATE EMPLOYEE
    @PostMapping
    public ApiResponse<EmployeeResponseDTO> create(
            @Valid @RequestBody EmployeeRequestDTO dto) {

        return ApiResponse.success(service.createEmployee(dto));
    }

    // ✏️ UPDATE EMPLOYEE
    @PutMapping("/{id}")
    public ApiResponse<EmployeeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO dto) {

        return ApiResponse.success(service.updateEmployee(id, dto));
    }

    // 🔍 GET SINGLE EMPLOYEE
    @GetMapping("/{id}")
    public ApiResponse<EmployeeResponseDTO> getById(@PathVariable Long id) {
        return ApiResponse.success(service.getEmployeeById(id));
    }

    // 📜 LIST (pagination + filters)
    @GetMapping
    public ApiResponse<Page<EmployeeResponseDTO>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.success(
                service.searchEmployees(name, department, status, page, size)
        );
    }

    // ❌ DELETE EMPLOYEE
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ApiResponse.success("Employee deleted successfully");
    }
}
