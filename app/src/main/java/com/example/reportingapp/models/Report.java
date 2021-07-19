package com.example.reportingapp.models;

public class Report {
    private String report_id;
    private String user_id;
    private String category_name;
    private String report;

    public Report(String report_id, String user_id, String category_name, String report) {
        this.report_id = report_id;
        this.user_id = user_id;
        this.category_name = category_name;
        this.report = report;
    }
    public Report() {

    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
