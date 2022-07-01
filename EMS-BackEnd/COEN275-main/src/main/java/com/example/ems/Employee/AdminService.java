package com.example.ems.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.ems.DB.DBHelper;

public class AdminService extends Salary {

    public boolean addEmployee(Employee emp) {
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();

        try {
            String query = " Insert into employee (id, NAME, address, email, type, salary_per_hour, Password)"
                    + " values (?, ?, ?, ?, ?,?,?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, emp.getId());
            statement.setString(2, emp.getName());
            statement.setString(3, emp.getAddress());
            statement.setString(4, emp.getEmail());
            statement.setString(5, emp.getType());
            statement.setDouble(6, emp.getSalaryPerHour());
            statement.setString(7, emp.getPassword());
            statement.execute();

        }catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean removeEmployee(Employee emp)
    {
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();
        try
        {
            String query = "Delete from employee where id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,emp.getId());
            statement.execute();
            conn.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }


    public PaySlip generatePaySlip(EmployeeHours emp) {
        PaySlip paySlip = new PaySlip();
        Employee employee = new Employee();
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();
        paySlip.setEmpId(emp.getId());
        try {
            PreparedStatement getSalary = conn.prepareStatement("" +
                    "Select sum(T.hours) AS TOTAL_HOURS , " +
                    "E.name, E.email, E.address, E.salary_per_hour, E.id , E.type " +
                    "from TimeManagement T " +
                    "join employee E ON T.employeeid = E.id" +
                    " where E.id = ? and T.month = ?");
            getSalary.setString(1, emp.getId());
            getSalary.setString(2, emp.getMonth());
            ResultSet resultSet = getSalary.executeQuery();
            if(resultSet.isBeforeFirst() ){
                EmployeeService employeeService = new EmployeeService();
                Employee e = new Employee();
                e.setId(emp.getId());
                employee = employeeService.getEmployee(e);
            } else {
                while (resultSet.next()) {
                    paySlip.setEmpId(emp.getId());
                    employee.setName(resultSet.getString("name"));
                    employee.setEmail(resultSet.getString("email"));
                    employee.setAddress(resultSet.getString("address"));
                    employee.setId(resultSet.getString("id"));
                    employee.setSalaryPerHour(resultSet.getDouble("salary_per_hour"));
                    employee.setType(resultSet.getString("type"));
                    int total_hours = resultSet.getInt("TOTAL_HOURS");
                    paySlip.setTotalSalary(salaryCalculator(total_hours, employee.getSalaryPerHour(), employee.getType()));
                    paySlip.setMonth(emp.getMonth());
                }
            }
            paySlip.setEmp(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paySlip;
    }
    public List<Employee> getAllEmployeeDetails(){
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();
        String query = "Select * from employee";
        List<Employee> list = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setName(resultSet.getString("name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setAddress(resultSet.getString("address"));
                employee.setType(resultSet.getString("type"));
                employee.setId(resultSet.getString("id"));
                employee.setSalaryPerHour(Double.parseDouble(resultSet.getString("salary_per_hour")));
                list.add(employee);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    double salaryCalculator(int total_hours, double salary_per_hour, String type) {
        return total_hours * salary_per_hour;
    }
}
