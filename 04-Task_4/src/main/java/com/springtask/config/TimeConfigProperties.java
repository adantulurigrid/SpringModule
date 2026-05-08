package com.springtask.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ConfigurationProperties(prefix = "app")
public class TimeConfigProperties {

    private LocalDate joiningDate;

    private LocalTime officeTime;

    private LocalDateTime deploymentTime;

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public LocalTime getOfficeTime() {
        return officeTime;
    }

    public void setOfficeTime(LocalTime officeTime) {
        this.officeTime = officeTime;
    }

    public LocalDateTime getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(LocalDateTime deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    @Override
    public String toString() {
        return "TimeConfigProperties{" +
                "joiningDate=" + joiningDate +
                ", officeTime=" + officeTime +
                ", deploymentTime=" + deploymentTime +
                '}';
    }
}
