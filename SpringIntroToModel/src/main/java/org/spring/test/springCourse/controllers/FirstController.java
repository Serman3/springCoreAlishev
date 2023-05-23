package org.spring.test.springCourse.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name", required = false) String name,      // Если в параметры ничего не пришло то requestparam кинет исключение, required=false игнорирует исклюение
                            @RequestParam(value = "surname", required = false) String surname,
                            Model model){
       /* String name = request.getParameter("name");
        String surname = request.getParameter("surname");*/
        model.addAttribute("message", "hello, " + name + " " + surname);
        System.out.println(name + " " + surname);
        return "hello";
    }

    @GetMapping("/goodbye")
    public String goodbyePage(){
        return "goodbye";
    }

    @GetMapping("/calculator")
    public String calculator(@RequestParam(value = "a", required = false) int a,
                             @RequestParam(value = "b", required = false) int b,
                             @RequestParam(value = "action", required = false) String action,
                             Model model){
        double result = 0;
        if (action.equals("multiplication")){
            result = a * b;
        } else if (action.equals("addition")){
            result = a + b;
        } else if (action.equals("subtraction")){
            result = a - b;
        } else if (action.equals("division")){
            result = a / (double) b;
        }
        model.addAttribute("result", result);
        return "calculator";
    }

}
