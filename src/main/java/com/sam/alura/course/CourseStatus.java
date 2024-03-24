package com.sam.alura.course;

public enum CourseStatus {
    ACTIVE("active"),
    INACTIVE("inactive");

    private String status;
    CourseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
