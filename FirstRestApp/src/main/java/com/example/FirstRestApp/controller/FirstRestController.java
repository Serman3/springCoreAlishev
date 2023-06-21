package com.example.FirstRestApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // @Controller + @ResponseBody над каждым методом;  @ResponseBody отправляет данные на сервер, конвертируя в json
@RequestMapping("api")
public class FirstRestController {

    @GetMapping("/sayHello")
    public String sayHello(){
        return "HelloWorld";
    }



}
