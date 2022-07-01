package com.example.ems;

import com.example.ems.DB.DBHelper;
import com.example.ems.Employee.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@CrossOrigin( origins = "*" )
@RestController
public class EmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmsApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/testDB")
    public String testDB() {
        Admin admin = new Admin();
        admin.setAdmin(true);
        admin.isAdmin();
        DBHelper dbHelper = new DBHelper();
        dbHelper.DBConnect();
        return String.format("DB Connected");
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public Employee loginUser(@RequestBody Employee employeeCred) {
        EmployeeService employeeService = new EmployeeService();
        Employee employee = employeeService.loginEmployee(employeeCred);
        return employee;
    }

    @PostMapping(value = "/getEmployeeDetail", consumes = "application/json", produces = "application/json")
    public Employee getEmployeeDetail(@RequestBody Employee employeeCred) {
        EmployeeService employeeService = new EmployeeService();
        Employee employee = employeeService.getEmployee(employeeCred);
        return employee;
    }

    @GetMapping("/getAllEmployees")
    public Map<String,List<Employee>> getAllEmployees() {
        AdminService adminService = new AdminService();
        List<Employee> list = adminService.getAllEmployeeDetails();
        Map<String,List<Employee>> map = new HashMap<>();
        map.put("result",list);
        return map;
    }

    @PostMapping(value = "/addEmployee", consumes = "application/json", produces ="application/json")
    public Employee addEmployee(@RequestBody Employee emp) {
        AdminService adminService = new AdminService();
        adminService.addEmployee(emp);
        return emp;
    }

    @PostMapping(value ="/removeEmployee", consumes = "application/json", produces = "application/json")
    public HashMap<String,Integer> removeEmployee(@RequestBody Employee emp) {
        AdminService adminService = new AdminService();
        boolean deleted = adminService.removeEmployee(emp);
        HashMap<String,Integer> map = new HashMap<>();
        map.put("status",200);
        return map;
    }

    @PostMapping(value ="/updateEmployee", consumes = "application/json", produces ="application/json")
    public Employee updateEmployee(@RequestBody Employee emp){
        EmployeeService employeeService = new EmployeeService();
        employeeService.updateEmployee(emp);
        return emp;
    }

    @GetMapping(value ="/getAllEmployeeHours", produces ="application/json")
    public List<EmployeeHours> allEmployeeHours(){
        EmpHoursService empHoursService = new EmpHoursService();
        ArrayList<EmployeeHours> empHours = empHoursService.employeeHours();
        return empHours;
    }

    @PostMapping(value ="/getEmployeeEnteredHours", consumes = "application/json", produces ="application/json")
    public EmployeeHours getEmployeeEnteredHours(@RequestBody Employee emp){
        EmpHoursService empHoursService = new EmpHoursService();
        EmployeeHours employeeHours = empHoursService.getEmployeeHours(emp);
        return employeeHours;
    }

    @PostMapping(value ="/updateHours", consumes = "application/json", produces ="application/json")
    public boolean updateHours(@RequestBody EmployeeHours empHours){
        EmpHoursService empHoursService = new EmpHoursService();
        empHoursService.updateHours(empHours);
        return true;
    }

    @PostMapping(value ="/approveHours", consumes = "application/json", produces ="application/json")
    public boolean approveHours(@RequestBody Employee emp){
        EmpHoursService empHoursService = new EmpHoursService();
        empHoursService.approveHours(emp);
        return true;
    }

    @PostMapping(value ="/generatePaySlip", consumes = "application/json", produces ="application/json")
    public PaySlip generatePaySlip(@RequestBody EmployeeHours emp){
        AdminService adminService = new AdminService();
        PaySlip paySlip = adminService.generatePaySlip(emp);
        return paySlip;
    }
}
