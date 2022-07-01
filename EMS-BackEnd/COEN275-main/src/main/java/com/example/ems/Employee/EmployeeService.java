package com.example.ems.Employee;

import com.example.ems.DB.DBHelper;

import java.sql.*;

public class EmployeeService extends Salary{

    public Employee loginEmployee(Employee credentials) {
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();

        String query = "Select * from employee where id = ?";
        Employee employee = new Employee();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, credentials.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                employee.setName(resultSet.getString("name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setAddress(resultSet.getString("address"));
                employee.setType(resultSet.getString("type"));
                employee.setId(resultSet.getString("id"));
                employee.setSalaryPerHour(Double.parseDouble(resultSet.getString("salary_per_hour")));
            }
            if (resultSet.next()){
                if (resultSet.getString("password") != credentials.getPassword()) {
                    System.out.println("Employee Not found");
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    public Employee getEmployee(Employee credentials) {
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();

        String query = "Select * from employee where id = ?";
        Employee employee = new Employee();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,credentials.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                employee.setName(resultSet.getString("name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setAddress(resultSet.getString("address"));
                employee.setType(resultSet.getString("type"));
                employee.setId(resultSet.getString("id"));
                employee.setSalaryPerHour(Double.parseDouble(resultSet.getString("salary_per_hour")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }



    public Employee updateEmployee(Employee emp) {
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();

        try {
            String query = "UPDATE employee set NAME = ?, address = ? , email = ?, salary_per_hour = ? where id = ? ";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, emp.getName());
            statement.setString(2, emp.getAddress());
            statement.setString(3, emp.getEmail());
            statement.setDouble(4, emp.getSalaryPerHour());
            statement.setString(5, emp.getId());
            statement.execute();

        }catch(Exception e) {
            e.printStackTrace();

        }
        return emp;
    }

    @Override
    double salaryCalculator(int totalHours, double salary_per_hour,String type) {
        double salary = totalHours * salary_per_hour;
        double exp = salary * 0.1;
        if(type == "admin") {
            return claculateBenifits(salary, exp, 0);
        }
        return claculateBenifits(salary, 0);
    }

    public double claculateBenifits(double salary, double expense, double taxes){
        return salary + expense + taxes;
    }

    public double claculateBenifits(double salary, double taxes){
        return salary + taxes;
    }
}
