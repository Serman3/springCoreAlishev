package com.example.SensorsRestApp.dto;

public class MeasurementRainyCountDTO {
    private int count;

    public MeasurementRainyCountDTO(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
