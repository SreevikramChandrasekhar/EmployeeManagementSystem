package com.example.ems.Employee;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Hours {

    Date date;
    int hours;
    String approved;

    public Date getDate() {
        return date;
    }

    public void setDate(String inputDate) {

        DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate ld = LocalDate.parse(inputDate, FORMAT);
        this.date = Date.valueOf(ld);
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }
}
