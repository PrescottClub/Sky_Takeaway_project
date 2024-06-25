package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        // 根据用户名查询员工信息
        Employee employee = employeeMapper.getByUsername(employeeLoginDTO.getUsername());

        // 如果员工不存在，返回 null
        if (employee == null) {
            return null; // 账号不存在
        }

        // 如果密码不匹配，返回一个密码错误的标识
        if (!employee.getPassword().equals(employeeLoginDTO.getPassword())) {
            employee.setPassword(null); // 清空密码，以表示密码错误
        }

        return employee;
    }

    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        // 将 employeeDTO 的属性映射到 employee 实体
        employee.setName(employeeDTO.getName());
        employee.setUsername(employeeDTO.getUsername());
        employee.setPassword(employeeDTO.getPassword());
        employee.setPhone(employeeDTO.getPhone());
        employee.setSex(employeeDTO.getSex());
        employee.setIdNumber(employeeDTO.getIdNumber());
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(1L); // 假设创建用户的 ID 是 1
        employee.setUpdateUser(1L);
        employee.setStatus(1); // 假设状态为 1 表示正常

        employeeMapper.insert(employee);
    }
}