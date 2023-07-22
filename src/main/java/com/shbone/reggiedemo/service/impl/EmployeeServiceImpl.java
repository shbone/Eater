package com.shbone.reggiedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shbone.reggiedemo.entity.Employee;
import com.shbone.reggiedemo.mapper.EmployeeMapper;
import com.shbone.reggiedemo.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
