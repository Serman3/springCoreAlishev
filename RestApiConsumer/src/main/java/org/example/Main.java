package org.example;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// Отправляем запросы к нашему Rest приложению Sensor, для демонстрации взаимодейсвия Rest приложений между собой
public class Main {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Random random = new Random();
        double min = -100.0; // Начальное значение диапазона - "от"
        double max = 100.0; // Конечное значение диапазона - "до
        String url = "http://localhost:8080/measurements";

        // Создаем сенсор
        //String url = "http://localhost:8080/sensors/registration";
      /*  for(int i = 0; i <= 1000; i++){
            Map<String, String> jsonToSend = new HashMap<>();
            jsonToSend.put("name", "sensorName-" + i);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);
            String response = restTemplate.postForObject(url, request, String.class);
            System.out.println(response);
        }*/

        // Создаем измерения
        //String url = "http://localhost:8080/measurements/add";
        /*for(int i = 0; i <= 1000; i++){
            Map<String, Object> jsonToSend = new HashMap<>();
            double randomValueTemperature = min + (Math.random() * max);
            jsonToSend.put("value", Math.round(randomValueTemperature * 100.0) / 100.0);
            jsonToSend.put("raining", random.nextBoolean());
            jsonToSend.put("sensor", new Sensor("sensorName-" + i));
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonToSend);
            String response = restTemplate.postForObject(url, request, String.class);
            System.out.println(response);
        }*/

        // Получаем измерения
        //String url = "http://localhost:8080/measurements";
        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);

    }
}