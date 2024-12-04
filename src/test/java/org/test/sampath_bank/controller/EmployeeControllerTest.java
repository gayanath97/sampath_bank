package org.test.sampath_bank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.test.sampath_bank.dto.EmployeeDTO;
import org.test.sampath_bank.service.EmployeeService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDTO sampleEmployeeDTO;

    @BeforeEach
    void setUp() {
        sampleEmployeeDTO = new EmployeeDTO();
        sampleEmployeeDTO.setId(1L);
        sampleEmployeeDTO.setFirstName("John");
        sampleEmployeeDTO.setLastName("Doe");
        sampleEmployeeDTO.setEmail("john.doe@example.com");
    }

    @Test
    void createEmployee_Success() {
        when(employeeService.saveEmployee(any())).thenReturn(sampleEmployeeDTO);

        ResponseEntity<EmployeeDTO> response = employeeController.createEmployee(sampleEmployeeDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleEmployeeDTO.getFirstName(), response.getBody().getFirstName());
    }

//    @Test
//    void getAllEmployees_Success() {
//        List<EmployeeDTO> employees = Arrays.asList(sampleEmployeeDTO);
//        when(employeeService.getAllEmployees()).thenReturn(employees);
//
//        ResponseEntity<List<EmployeeDTO>> response = employeeController.getAllEmployees();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(1, response.getBody().size());
//    }

    @Test
    void getEmployeeById_Success() {
        when(employeeService.getEmployeeById(1L)).thenReturn(sampleEmployeeDTO);

        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleEmployeeDTO.getId(), response.getBody().getId());
    }

    @Test
    void updateEmployee_Success() {
        when(employeeService.updateEmployee(eq(1L), any())).thenReturn(sampleEmployeeDTO);

        ResponseEntity<EmployeeDTO> response = employeeController.updateEmployee(1L, sampleEmployeeDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sampleEmployeeDTO.getFirstName(), response.getBody().getFirstName());
    }

    @Test
    void deleteEmployee_Success() {
        doNothing().when(employeeService).deleteEmployee(1L);

        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(1L);
    }
} 