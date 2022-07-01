package com.example.ems.Employee;

import com.example.ems.DB.DBHelper;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class EmpHoursService{

    public ArrayList<EmployeeHours> employeeHours() {
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();
        String query = "Select * from TimeManagement where approved = 'NO'";
        ArrayList<EmployeeHours> empHours = new ArrayList<EmployeeHours>();
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                EmployeeHours hour = new EmployeeHours();
                hour.setTotalHours(resultSet.getInt("hours"));
                hour.setId(resultSet.getString("employeeid"));
                empHours.add(hour);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<EmployeeHours> totalHours = calculateHours(empHours);
        return totalHours;
    }

    private ArrayList<EmployeeHours> calculateHours(ArrayList<EmployeeHours> empHours) {
        HashMap<String,Integer> map = new HashMap<>();
        ArrayList<EmployeeHours> employeeHours = new ArrayList<>();
        for(EmployeeHours emp : empHours){
            map.put(emp.getId(),map.getOrDefault(emp.getId(),0)+emp.getTotalHours());
        }
        for(Map.Entry<String,Integer> e : map.entrySet()){
            EmployeeHours ehrs = new EmployeeHours();
            ehrs.setId(e.getKey());
            ehrs.setTotalHours(e.getValue());
            ehrs.setMonth("MARCH");
            employeeHours.add(ehrs);
        }

        return employeeHours;
    }

    public boolean updateHours(EmployeeHours empHours) {
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();
        try {
            for(Hours emp : empHours.getEmpHours()) {
                if(entryExists(emp.getDate(),empHours.getId())){
                    String query = "UPDATE TimeManagement SET hours = ?" +
                            " where employeeid = ?";
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.setInt(1, emp.getHours());
                    statement.setString(2, empHours.getId());
                    statement.execute();
                } else {
                    String query = "Insert into TimeManagement (approved, hours, date, month, employeeid)" + " values ('NO', ?, ?, ?, ?)";
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.setInt(1, emp.getHours());
                    statement.setDate(2, emp.getDate());
                    statement.setString(3, getMonthByDate(emp.getDate()));
                    statement.setString(4, empHours.getId());
                    statement.execute();
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean entryExists(Date date, String id) {
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();
        try {
            String query = "Select * from TimeManagement where employeeid = ? and Date = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, id);
            statement.setDate(2, date);
            ResultSet resultSet = statement.executeQuery();{
                if(!resultSet.isBeforeFirst() ){
                    return false;
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void approveHours(Employee emp) {
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();
        try {
            String query = "UPDATE TimeManagement set approved = 'YES' where employeeid = ? ";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, emp.getId());
            statement.execute();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private String getMonthByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return getMonth(month);
    }

    private String getMonth(int m){
        String[] months = new String[]{"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        return months[m];
    }

    public EmployeeHours getEmployeeHours(Employee emp) {
        DBHelper dbHelper = new DBHelper();
        Connection conn = dbHelper.DBConnect();
        EmployeeHours employeeHours = new EmployeeHours();
        try {
            String query = "Select * from TimeManagement where employeeid = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, emp.getId());
            List<Hours> hours = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Hours h = new Hours();
                h.setHours(resultSet.getInt("hours"));
                h.setDate(resultSet.getString("date"));
                h.setApproved(resultSet.getString("approved"));
                hours.add(h);
            }
            employeeHours.setEmpHours(hours);
            employeeHours.setId(emp.getId());
        }catch(Exception e) {
            e.printStackTrace();
        }

        return employeeHours;
    }
}
