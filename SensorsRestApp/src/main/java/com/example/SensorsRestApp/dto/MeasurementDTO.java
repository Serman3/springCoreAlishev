package com.example.SensorsRestApp.dto;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class MeasurementDTO {

    @NotNull(message = "Value should not be empty")
    @DecimalMin(value = "-100", message = "Value should be between -100 and 100 degrees")
    @DecimalMax(value = "100", message = "Value should be between -100 and 100 degrees")
    private Double value;

    @Column(name = "raining")
    @NotNull(message = "Raining should not be empty")
    private Boolean raining;

    @Valid
    @NotNull(message = "Sensor should not be empty")
    private SensorDTO sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
