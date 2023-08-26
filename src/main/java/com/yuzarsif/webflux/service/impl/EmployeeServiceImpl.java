package com.yuzarsif.webflux.service.impl;

import com.yuzarsif.webflux.dto.EmployeeDto;
import com.yuzarsif.webflux.entity.Employee;
import com.yuzarsif.webflux.mapper.EmployeeMapper;
import com.yuzarsif.webflux.repository.EmployeeRepository;
import com.yuzarsif.webflux.service.EmployeeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Mono<Employee> savedEmployee = repository.save(employee);
        return savedEmployee.map((emp) -> EmployeeMapper.mapToEmployeeDto(emp));
    }

    @Override
    public Mono<EmployeeDto> getEmployee(String id) {

        Mono<Employee> employeeFromDb = repository.findById(id);

        return employeeFromDb
                .map((employee -> EmployeeMapper.mapToEmployeeDto(employee)));
    }

    @Override
    public Flux<EmployeeDto> getAllEmployees() {

        Flux<Employee> employeeFlux = repository.findAll();

        return employeeFlux
                .map((employee -> EmployeeMapper.mapToEmployeeDto(employee)))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String id) {

        Mono<Employee> employeeMono = repository.findById(id);

        Mono<Employee> updatedEmployee = employeeMono.flatMap((existingEmployee) -> {
            existingEmployee.setFirstName(employeeDto.getFirstName());
            existingEmployee.setLastName(employeeDto.getLastName());
            existingEmployee.setEmail(employeeDto.getEmail());

            return repository.save(existingEmployee);
        });

        return updatedEmployee
                .map((employee -> EmployeeMapper.mapToEmployeeDto(employee)));
    }

    @Override
    public Mono<Void> deleteEmployee(String id) {
        return repository.deleteById(id);
    }
}
