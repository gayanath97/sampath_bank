package org.test.sampath_bank.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.test.sampath_bank.dto.EmployeeDTO;
import org.test.sampath_bank.model.Employee;
import java.util.List;

public interface EmployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployeeById(Long id);
    Page<EmployeeDTO> getAllEmployees(Pageable pageable);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
}
