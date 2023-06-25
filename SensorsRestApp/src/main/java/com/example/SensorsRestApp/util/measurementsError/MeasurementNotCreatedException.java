package com.example.SensorsRestApp.util.measurementsError;

public class MeasurementNotCreatedException extends RuntimeException{

    public MeasurementNotCreatedException(String message){
        super(message);
    }
}
