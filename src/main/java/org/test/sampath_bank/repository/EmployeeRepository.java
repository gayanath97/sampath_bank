package org.test.sampath_bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.test.sampath_bank.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
