package com.shbone.reggiedemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shbone.reggiedemo.common.BaseContext;
import com.shbone.reggiedemo.common.R;
import com.shbone.reggiedemo.entity.Employee;
import com.shbone.reggiedemo.service.EmployeeService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.RequestWrapper;
import java.time.LocalDateTime;

/**
 * @author sunhb
 */
@Slf4j
@Controller
@RequestMapping("/employee")
@ResponseBody
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//       查看数据库中该数据名字
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<Employee>();
        lambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());

        Employee employee1 = employeeService.getOne(lambdaQueryWrapper);
//        判断是否没有查到
        if(employee1 == null){
            return R.error("用户不存在");
        }

        if(!employee1.getPassword().equals(password)){
            return R.error("密码错误");
        }

        if(employee1.getStatus() == 0){
            return R.error("账户已被禁用");
        }

        request.getSession().setAttribute("employee",employee1.getId());

        long id = Thread.currentThread().getId();
        log.info("login 线程号:"+id);


        System.out.println("login session id: "+request.getSession().getId());
        return R.success(employee1);
    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest servletRequest){
        servletRequest.getSession().removeAttribute("employee");
        return  R.success("成功退出");
    }
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("增加员工信息,{}",employee.toString());
//        初始密码并加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());
        //
        // Long empId = (Long)request.getSession().getAttribute("employee");
        // System.out.println(request.getSession().getAttribute("employee"));
        // System.out.println("empId: "+empId);
        // employee.setCreateUser(empId);
        // employee.setUpdateUser(empId);
        employeeService.save(employee);
        System.out.println("save session id: "+request.getSession().getId());
        return  R.success("成功插入用户"+employee.getName());
    }


    @GetMapping("/page")
    public R<Page> pageR(Integer page, Integer pageSize, String name){
        log.info("page:{}\tpageSize:{}\tname:{}",page.toString(),pageSize.toString(),name);
//        构造分页器
        Page pageInfo = new Page(page,pageSize);
        //构造分页条件
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getUsername,name);

        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo,queryWrapper);
        return  R.success(pageInfo);
    }
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());
        //更新数据库
        // Long id = employee.getId();
        // Long employee1 = (Long)request.getAttribute("employee");
        // employee.setUpdateTime(LocalDateTime.now());
        // employee.setUpdateUser(employee1);

        // Long userId = (Long) request.getSession().getAttribute("employee");
        // log.info("userId:{}",userId);
        // BaseContext.setThreadLocal(userId);
        long id = Thread.currentThread().getId();
        log.info("update 线程号:"+id);
        employeeService.updateById(employee);

        return R.success("更改成功");

    }

    @GetMapping("/{id}")
    public R<Object> findEmployee(HttpServletRequest request,@PathVariable("id") Long id ){
        log.info("id:{}",id);
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.success("没有查找到员工信息");
    }


}
