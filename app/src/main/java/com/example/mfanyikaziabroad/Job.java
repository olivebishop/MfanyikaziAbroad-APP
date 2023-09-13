package com.example.mfanyikaziabroad;

public class Job {
    private String title;
    private String description;
    private String type;
    private String salary_range;

    public Job(String title, String description, String type, String salary_range) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.salary_range = salary_range;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSalary_range() {
        return salary_range;
    }

    public void setSalary_range(String salary_range) {
        this.salary_range = salary_range;
    }
}
