package com.yuzarsif.webflux.mapper;

import com.yuzarsif.webflux.dto.EmployeeDto;
import com.yuzarsif.webflux.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee from) {
        return new EmployeeDto(
                from.getId(),
                from.getFirstName(),
                from.getLastName(),
                from.getEmail());
    }

    public static Employee mapToEmployee(EmployeeDto from) {
        return new Employee(
                from.getId(),
                from.getFirstName(),
                from.getLastName(),
                from.getEmail());
    }
}
