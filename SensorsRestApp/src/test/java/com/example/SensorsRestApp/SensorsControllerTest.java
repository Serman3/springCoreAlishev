package com.example.SensorsRestApp;

import com.example.SensorsRestApp.controllers.SensorsController;
import com.example.SensorsRestApp.dto.SensorDTO;
import com.example.SensorsRestApp.models.Sensor;
import com.example.SensorsRestApp.services.SensorsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import java.util.stream.Collectors;


@ExtendWith(MockitoExtension.class)
class SensorsControllerTest {

	@Mock
	private SensorsService sensorsService;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private SensorsController sensorsController;

	@Test
	public void handleGetAllSensorsTest_ReturnsValidResponseEntity() throws Exception {
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



}
