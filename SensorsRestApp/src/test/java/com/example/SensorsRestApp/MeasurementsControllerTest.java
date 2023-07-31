package com.example.SensorsRestApp;

import com.example.SensorsRestApp.controllers.MeasurementsController;
import com.example.SensorsRestApp.dto.MeasurementDTO;
import com.example.SensorsRestApp.dto.MeasurementRainyCountDTO;
import com.example.SensorsRestApp.dto.SensorDTO;
import com.example.SensorsRestApp.models.Measurement;
import com.example.SensorsRestApp.models.Sensor;
import com.example.SensorsRestApp.services.MeasurementsService;
import com.example.SensorsRestApp.utill.validators.MeasurementValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MeasurementsControllerTest {

    @Mock
    private MeasurementValidator measurementValidator;

    @Mock
    private MeasurementsService measurementsService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MeasurementsController measurementsController; // Тестируемый класс

    @Test
    @DisplayName("POST /measurements/add Создает новое измерение и возвращает HTTP-ответ со статусом 200 OK")
    public void createNewMeasurement_MeasurementIsValid_ReturnsValidResponseEntity(){
        //given дано
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("sensorName");
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setRaining(true);
        measurementDTO.setValue(13.5);
        measurementDTO.setSensor(sensorDTO);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        //when когда
        var responseEntity = this.measurementsController.createMeasurement(measurementDTO, bindingResult);
        //then тогда
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        if (responseEntity.getBody() instanceof MeasurementDTO measurementDTO1) {
            assertNotNull(measurementDTO1.getSensor());
            assertEquals(measurementDTO.getSensor(), sensorDTO);
        }else {
            assertInstanceOf(MeasurementDTO.class, responseEntity.getBody());
        }
        Mockito.verify(this.measurementsService).createMeasurement(modelMapper.map(measurementDTO, Measurement.class)); //  Проверка на вызывался ли метод save в sensorService
        Mockito.verifyNoMoreInteractions(this.measurementsService);  // проверка что больше не было обращений к сервису
    }

    @Test
    @DisplayName("GET /measurements Возвращает HTTP-ответ со статусом 200 OK и списком измерений")
    public void getMeasurementsTest_ReturnsValidResponseEntity() throws Exception {
        var measurements = List.of(new Measurement(13.5, true, new Sensor("name1"), LocalTime.now()), new Measurement(17.5, false, new Sensor("name2"), LocalTime.now()));
        Mockito.doReturn(measurements).when(this.measurementsService).findAll();

        //when когда

        var responseEntity = this.measurementsController.getMeasurements();

        //then тогда
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(measurements.stream().map(measurement -> modelMapper.map(measurement, MeasurementDTO.class)).collect(Collectors.toList()), responseEntity.getBody());
    }

    @Test
    @DisplayName("GET /rainyDaysCount Возвращает HTTP-ответ со статусом 200 OK и количеством дождливых дней")
    public void getSensorTest_ReturnsValidResponseEntity(){
        //given дано
        Mockito.doReturn(5).when(this.measurementsService).getRainyDaysCount();
        //when когда
        var responseEntity = this.measurementsController.rainyDaysCount();
        //then тогда
        assertNotNull(responseEntity);
        if (responseEntity.getBody() instanceof MeasurementRainyCountDTO mrc){
            assertEquals(5, mrc.getCount());
        }
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
    }
}
