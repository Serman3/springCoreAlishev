package com.example.SensorsRestApp.dto;

public class MeasurementCountDTO {
    private int count;

    public MeasurementCountDTO(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
