
package com.codenzic.employeemanagement.service;

import com.codenzic.employeemanagement.dto.EmployeeRequest;
import com.codenzic.employeemanagement.dto.EmployeeResponse;
import com.codenzic.employeemanagement.entity.Employee;
import com.codenzic.employeemanagement.exception.DuplicateEmailException;
import com.codenzic.employeemanagement.exception.EmployeeNotFoundException;
import com.codenzic.employeemanagement.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Create an employee
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        String email = normalizeEmail(request.getEmail());

        if (employeeRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }

        Employee employee = new Employee(
                request.getFirstName().trim(),
                request.getLastName().trim(),
                email,
                normalizeDepartment(request.getDepartment()),
                request.getSalary()
        );

        Employee savedEmployee = employeeRepository.save(employee);

        return mapToResponse(savedEmployee);
    }

    // Get all employees
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get employee by ID
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = findEmployeeById(id);
        return mapToResponse(employee);
    }

    // Update employee
    public EmployeeResponse updateEmployee(
            Long id, EmployeeRequest request) {

        Employee employee = findEmployeeById(id);
        String email = normalizeEmail(request.getEmail());

        employeeRepository.findByEmail(email)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateEmailException(email);
                });

        employee.setFirstName(request.getFirstName().trim());
        employee.setLastName(request.getLastName().trim());
        employee.setEmail(email);
        employee.setDepartment(
                normalizeDepartment(request.getDepartment())
        );
        employee.setSalary(request.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);

        return mapToResponse(updatedEmployee);
    }

    // Delete employee
    public void deleteEmployee(Long id) {
        Employee employee = findEmployeeById(id);
        employeeRepository.delete(employee);
    }

    // Find employee or throw an exception
    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    // Convert entity to response DTO
    private EmployeeResponse mapToResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }

    // Normalize email for consistent duplicate checks
    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private String normalizeDepartment(String department) {
        if (department == null || department.isBlank()) {
            return null;
        }
        return department.trim();
    }
}
