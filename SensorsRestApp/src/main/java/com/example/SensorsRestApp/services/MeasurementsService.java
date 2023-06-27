package com.example.SensorsRestApp.services;

import com.example.SensorsRestApp.models.Measurement;
import com.example.SensorsRestApp.models.Sensor;
import com.example.SensorsRestApp.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    @Autowired
    private final MeasurementsRepository measurementsRepository;
    @Autowired
    private final SensorsService sensorsService;

    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    /*@Transactional
    public void createMeasurement(Measurement measurement){
        Sensor sensor = sensorsService.findBySensorName(measurement.getSensor().getName()).orElse(null);
        measurement.setSensor(sensor);
        measurement.setTime(LocalTime.now());
        measurementsRepository.save(measurement);
    }*/

    @Transactional
    public void createMeasurement(Measurement measurement){
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }

    public void enrichMeasurement(Measurement measurement){
        Sensor sensor = sensorsService.findBySensorName(measurement.getSensor().getName()).orElse(null);
        measurement.setSensor(sensor);
        measurement.setTime(LocalTime.now());
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    public int getRainyDaysCount(){
        return measurementsRepository.countRainyDaysTrue();
    }
}
