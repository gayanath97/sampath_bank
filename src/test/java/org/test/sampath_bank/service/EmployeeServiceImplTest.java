package org.test.sampath_bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.test.sampath_bank.dto.EmployeeDTO;
import org.test.sampath_bank.model.Employee;
import org.test.sampath_bank.repository.EmployeeRepository;
import org.test.sampath_bank.exception.ResourceNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

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
        when(employeeRepository.save(any())).thenReturn(convertToEntity(sampleEmployeeDTO));

        EmployeeDTO result = employeeService.saveEmployee(sampleEmployeeDTO);

        assertNotNull(result);
        assertEquals(sampleEmployeeDTO.getFirstName(), result.getFirstName());
        assertEquals(sampleEmployeeDTO.getLastName(), result.getLastName());
        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    void getAllEmployees_Success() {
        List<Employee> employees = Arrays.asList(convertToEntity(sampleEmployeeDTO));
        Page<Employee> employeePage = new PageImpl<>(employees);
        
        when(employeeRepository.findAll(any(PageRequest.class))).thenReturn(employeePage);

        Page<EmployeeDTO> result = employeeService.getAllEmployees(PageRequest.of(0, 10));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(sampleEmployeeDTO.getFirstName(), result.getContent().get(0).getFirstName());
        verify(employeeRepository).findAll(any(PageRequest.class));
    }

    @Test
    void getAllEmployees_WhenEmpty_ReturnsEmptyPage() {
        Page<Employee> emptyPage = new PageImpl<>(Arrays.asList());
        when(employeeRepository.findAll(any(PageRequest.class))).thenReturn(emptyPage);

        Page<EmployeeDTO> result = employeeService.getAllEmployees(PageRequest.of(0, 10));

        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void getEmployeeById_Success() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(convertToEntity(sampleEmployeeDTO)));

        EmployeeDTO result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(sampleEmployeeDTO.getId(), result.getId());
        assertEquals(sampleEmployeeDTO.getFirstName(), result.getFirstName());
    }

    @Test
    void getEmployeeById_WhenNotFound_ThrowsException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(99L));
    }

    @Test
    void updateEmployee_Success() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(convertToEntity(sampleEmployeeDTO)));
        when(employeeRepository.save(any())).thenReturn(convertToEntity(sampleEmployeeDTO));

        EmployeeDTO result = employeeService.updateEmployee(1L, sampleEmployeeDTO);

        assertNotNull(result);
        assertEquals(sampleEmployeeDTO.getFirstName(), result.getFirstName());
        verify(employeeRepository, times(1)).save(any());
    }

    @Test
    void updateEmployee_WhenNotFound_ThrowsException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            employeeService.updateEmployee(99L, sampleEmployeeDTO));
    }

    @Test
    void deleteEmployee_Success() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(convertToEntity(sampleEmployeeDTO)));
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteEmployee_WhenNotFound_ThrowsException() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(99L));
    }

    @Test
    void createEmployee_WithNullEmployee_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> employeeService.saveEmployee(null));
    }

    private Employee convertToEntity(EmployeeDTO dto) {
        Employee entity = new Employee();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        return entity;
    }
} 