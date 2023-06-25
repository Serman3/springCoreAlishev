package com.example.SensorsRestApp.services;

import com.example.SensorsRestApp.models.Measurement;
import com.example.SensorsRestApp.models.Sensor;
import com.example.SensorsRestApp.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    @Transactional
    public void createMeasurement(Measurement measurement){
        Sensor sensor = sensorsService.findBySensorName(measurement.getSensor().getName()).orElse(null);
        measurement.setSensor(sensor);
        measurementsRepository.save(measurement);
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    public int getRainyDaysCount(){
        return measurementsRepository.countRainyDaysTrue();
    }
}
