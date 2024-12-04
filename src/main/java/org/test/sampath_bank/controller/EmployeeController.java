package org.test.sampath_bank.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.sampath_bank.dto.EmployeeDTO;
import org.test.sampath_bank.service.EmployeeServiceImpl;
import org.test.sampath_bank.model.Employee;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "APIs for managing employees")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
        summary = "Create a new employee",
        description = "Creates a new employee record in the system"
    )
    @ApiResponse(responseCode = "201", description = "Employee created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.saveEmployee(employeeDTO));
    }

    @Operation(
        summary = "Get all employees",
        description = "Retrieves a paginated list of all employees in the system with sorting capabilities"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved employees")
    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(
        @Parameter(description = "Page number (0-based)") 
        @RequestParam(defaultValue = "0") int page,
        
        @Parameter(description = "Number of items per page") 
        @RequestParam(defaultValue = "10") int size,
        
        @Parameter(description = "Sort field (e.g., firstName, lastName, email, salary)") 
        @RequestParam(defaultValue = "id") String sort,
        
        @Parameter(description = "Sort direction (ASC or DESC)") 
        @RequestParam(defaultValue = "ASC") String direction
    ) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
    }

    @Operation(
        summary = "Get employee by ID",
        description = "Retrieves an employee by their ID"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved employee")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(
        @Parameter(description = "ID of the employee to retrieve") 
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(
        summary = "Update employee",
        description = "Updates an existing employee's information"
    )
    @ApiResponse(responseCode = "200", description = "Employee updated successfully")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
        @Parameter(description = "ID of the employee to update") 
        @PathVariable Long id,
        @Valid @RequestBody EmployeeDTO employeeDTO
    ) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    @Operation(
        summary = "Delete employee",
        description = "Deletes an employee from the system"
    )
    @ApiResponse(responseCode = "204", description = "Employee deleted successfully")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
        @Parameter(description = "ID of the employee to delete") 
        @PathVariable Long id
    ) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
