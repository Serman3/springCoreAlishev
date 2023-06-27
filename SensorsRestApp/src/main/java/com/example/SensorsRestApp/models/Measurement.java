package com.example.SensorsRestApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "measurement")
public class Measurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "value")
    @NotNull(message = "Value should not be empty")
    @DecimalMin(value = "-100", message = "Value should be between -100 and 100 degrees")
    @DecimalMax(value = "100", message = "Value should be between -100 and 100 degrees")
    private double value;

    @Column(name = "raining")
    @NotNull(message = "Raining should not be empty")
    private boolean raining;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor", referencedColumnName = "name", nullable = false)
    private Sensor sensor;

    @Column(name = "time")
    private LocalTime time;

    public Measurement(){};

    public Measurement(double value, boolean raining, Sensor sensor, LocalTime time) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
