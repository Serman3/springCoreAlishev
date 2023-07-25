package com.example.SensorsRestApp;

import com.example.SensorsRestApp.controllers.SensorsController;
import com.example.SensorsRestApp.dto.SensorDTO;
import com.example.SensorsRestApp.models.Sensor;
import com.example.SensorsRestApp.services.SensorsService;
import com.example.SensorsRestApp.utill.validators.SensorValidator;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class SensorsControllerTest {

	@Mock
	private SensorsService sensorsService;

    @Mock
    private SensorValidator sensorValidator;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private SensorsController sensorsController;

	@Test
    @DisplayName("GET /sensors Возвращает HTTP-ответ со статусом 200 OK и списком сенсоров")
	public void getAllSensorsTest_ReturnsValidResponseEntity() throws Exception {
		//given дано
		var sensors = List.of(new Sensor("имя1"), new Sensor("имя2"));
		Mockito.doReturn(sensors).when(this.sensorsService).findAll();

		//when когда

		var responseEntity = this.sensorsController.getSensors();

		//then тогда
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
		assertEquals(sensors.stream().map(sensor -> modelMapper.map(sensor, SensorDTO.class)).collect(Collectors.toList()), responseEntity.getBody());
	}

    @Test
    @DisplayName("POST /sensors/registration Создает новый сенсор и возвращает HTTP-ответ со статусом 200 OK")
    public void createNewSensors_SensorIsValid_ReturnsValidResponseEntity(){
        //given дано
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("Название сенсора");
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        //when когда
        var responseEntity = this.sensorsController.createSensor(sensorDTO, bindingResult);
        //then тогда
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
		if (responseEntity.getBody() instanceof SensorDTO sensorDTO1){
			assertNotNull(sensorDTO1.getName());
		}else {
			assertInstanceOf(SensorDTO.class, responseEntity.getBody());
		}
		Mockito.verify(this.sensorsService).save(modelMapper.map(sensorDTO, Sensor.class)); //  Проверка на вызывался ли метод save в sensorService
		Mockito.verifyNoMoreInteractions(this.sensorsService);  // проверка что больше не было обращений к сервису
    }


}
