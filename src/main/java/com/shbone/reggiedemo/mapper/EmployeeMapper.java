package com.shbone.reggiedemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shbone.reggiedemo.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author sunhb
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
