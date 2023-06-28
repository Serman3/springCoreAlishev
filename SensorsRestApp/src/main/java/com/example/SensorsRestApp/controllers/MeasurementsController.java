package com.example.SensorsRestApp.controllers;

import com.example.SensorsRestApp.dto.MeasurementDTO;
import com.example.SensorsRestApp.dto.MeasurementRainyCountDTO;
import com.example.SensorsRestApp.models.Measurement;
import com.example.SensorsRestApp.services.MeasurementsService;
import com.example.SensorsRestApp.utill.measurementsError.MeasurementErrorResponse;
import com.example.SensorsRestApp.utill.measurementsError.MeasurementNotCreatedException;
import com.example.SensorsRestApp.utill.measurementsError.MeasurementNotFoundException;
import com.example.SensorsRestApp.utill.validators.MeasurementValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementValidator measurementValidator;

    private final MeasurementsService measurementsService;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementValidator measurementValidator, MeasurementsService measurementsService, ModelMapper modelMapper) {
        this.measurementValidator = measurementValidator;
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult){
        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);
        measurementsService.createMeasurement(measurement);
        //return ResponseEntity.ok().body(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements(){
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public MeasurementRainyCountDTO rainyDaysCount(){
        return new MeasurementRainyCountDTO(measurementsService.getRainyDaysCount());
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
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

