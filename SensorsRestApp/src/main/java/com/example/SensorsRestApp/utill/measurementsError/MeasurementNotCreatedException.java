package com.example.SensorsRestApp.utill.measurementsError;

public class MeasurementNotCreatedException extends RuntimeException{

    public MeasurementNotCreatedException(String message){
        super(message);
    }
}

