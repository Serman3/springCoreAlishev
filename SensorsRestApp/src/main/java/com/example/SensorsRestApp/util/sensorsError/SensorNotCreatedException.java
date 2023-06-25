package com.example.SensorsRestApp.util.sensorsError;

public class SensorNotCreatedException extends RuntimeException{
    public SensorNotCreatedException(String msg){
        super(msg);
    }
}
