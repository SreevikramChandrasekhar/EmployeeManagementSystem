package com.example.ems.Employee;

import java.util.List;

public class EmployeeHours {

    String id;
    String month;
    int totalHours;
    List<Hours> empHours;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Hours> getEmpHours() {
        return empHours;
    }

    public void setEmpHours(List<Hours> empHours) {
        this.empHours = empHours;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }
}
