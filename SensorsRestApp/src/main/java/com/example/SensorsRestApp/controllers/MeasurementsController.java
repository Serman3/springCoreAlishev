package com.example.SensorsRestApp.controllers;

import com.example.SensorsRestApp.dto.MeasurementCountDTO;
import com.example.SensorsRestApp.models.Measurement;
import com.example.SensorsRestApp.services.MeasurementsService;
import com.example.SensorsRestApp.util.validators.MeasurementValidator;
import com.example.SensorsRestApp.util.measurementsError.MeasurementErrorResponse;
import com.example.SensorsRestApp.util.measurementsError.MeasurementNotCreatedException;
import com.example.SensorsRestApp.util.measurementsError.MeasurementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementValidator measurementValidator;
    private final MeasurementsService measurementsService;

    @Autowired
    public MeasurementsController(MeasurementValidator measurementValidator, MeasurementsService measurementsService) {
        this.measurementValidator = measurementValidator;
        this.measurementsService = measurementsService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createMeasurement(@RequestBody @Valid Measurement measurement, BindingResult bindingResult){
        measurementValidator.validate(measurement, bindingResult);
        measurementsService.createMeasurement(measurement);
        //return ResponseEntity.ok().body(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<Measurement> getMeasurements(){
        return measurementsService.findAll();
    }

    @GetMapping("/rainyDaysCount")
    public MeasurementCountDTO rainyDaysCount(){
       return new MeasurementCountDTO(measurementsService.getRainyDaysCount());
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException exception){
        MeasurementErrorResponse response = new MeasurementErrorResponse(exception.getMessage(), System.currentTimeMillis());
        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotFoundException exception){
        MeasurementErrorResponse response = new MeasurementErrorResponse("Дождливых дней нет", System.currentTimeMillis());
        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
