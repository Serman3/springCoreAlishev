package com.example.SensorsRestApp.controllers;

import com.example.SensorsRestApp.models.Sensor;
import com.example.SensorsRestApp.services.SensorsService;
import com.example.SensorsRestApp.util.sensorsError.SensorErrorResponse;
import com.example.SensorsRestApp.util.sensorsError.SensorNotCreatedException;
import com.example.SensorsRestApp.util.sensorsError.SensorNotFoundException;
import com.example.SensorsRestApp.util.validators.SensorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping()
    public List<Sensor> getSensors() {
        return sensorsService.findAll(); // Jackson конвертирует эти объекты в JSON
    }

    @GetMapping("/{id}")
    public Sensor getSensor(@PathVariable("id") int id) {
        return sensorsService.findOne(id); // Jackson конвертирует в JSON
    }

    @PostMapping("/registration")
    public ResponseEntity<Sensor> createSensor(@RequestBody @Valid Sensor sensor, BindingResult bindingResult){
        sensorValidator.validate(sensor, bindingResult);
        sensorsService.save(sensor);
        return ResponseEntity.ok().body(sensor);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException exception){
        SensorErrorResponse response = new SensorErrorResponse("Такого сенсора не существует", System.currentTimeMillis());
        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException exception){
        SensorErrorResponse response = new SensorErrorResponse(exception.getMessage(), System.currentTimeMillis());
        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
