package com.ssmp.controller;

import com.ssmp.pojo.Employee;
import com.ssmp.service.IEmployeeService;
import com.ssmp.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 登陆
     * @param name
     * @param pwd
     * @return
     */
    @GetMapping("/{name}/{pwd}")
    public Result login(@PathVariable String name, @PathVariable String pwd) {
        return employeeService.login(name,pwd);
    }

    /**
     * 登出
     * @return
     */
    @GetMapping
    public Result logout(){
        return employeeService.logout();
    }
}
