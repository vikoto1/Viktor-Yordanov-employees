package com.example.demo3;

public class PairData {
    private int employee1;
    private int employee2;
    private int projectID;
    private int daysWorked;

    public PairData(int employee1, int employee2, int projectID, int daysWorked) {
        this.employee1 = employee1;
        this.employee2 = employee2;
        this.projectID = projectID;
        this.daysWorked = daysWorked;
    }

    public int getEmployee1() {
        return employee1;
    }

    public void setEmployee1(int employee1) {
        this.employee1 = employee1;
    }

    public int getEmployee2() {
        return employee2;
    }

    public void setEmployee2(int employee2) {
        this.employee2 = employee2;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }
}

