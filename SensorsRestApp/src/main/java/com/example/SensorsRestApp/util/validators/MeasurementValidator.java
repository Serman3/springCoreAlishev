package com.example.SensorsRestApp.util.validators;

import com.example.SensorsRestApp.models.Measurement;
import com.example.SensorsRestApp.services.SensorsService;
import com.example.SensorsRestApp.util.measurementsError.MeasurementNotCreatedException;
import com.example.SensorsRestApp.util.sensorsError.SensorNotCreatedException;
import com.example.SensorsRestApp.util.sensorsError.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import java.util.List;

@Component
public class MeasurementValidator implements Validator {

    private SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (sensorsService.findBySensorName(measurement.getSensor().getName()).isEmpty()){
            throw new MeasurementNotCreatedException("Такой сенсор не сущесвует в бд");
        }

        if(errors.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrorList = errors.getFieldErrors();
            for (FieldError error : fieldErrorList) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new MeasurementNotCreatedException(errorMessage.toString());
        }
    }

}
