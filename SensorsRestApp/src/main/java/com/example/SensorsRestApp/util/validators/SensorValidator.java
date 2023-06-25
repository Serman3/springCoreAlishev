package com.example.SensorsRestApp.util.validators;

import com.example.SensorsRestApp.models.Sensor;
import com.example.SensorsRestApp.services.SensorsService;
import com.example.SensorsRestApp.util.sensorsError.SensorNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if(sensorsService.findBySensorName(sensor.getName()).isPresent()){
            throw new SensorNotCreatedException("Сенсор с таким именем уже существут");
        }

        if(errors.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrorList = errors.getFieldErrors();
            for(FieldError error : fieldErrorList){
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new SensorNotCreatedException(errorMessage.toString());
        }
    }

}
