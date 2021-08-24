package top.noahlin.blog4u.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ThymeleafController {

    @GetMapping("/thymeleaf")
    public String hello(HttpServletRequest request, @RequestParam(value = "description", required = false, defaultValue = "Spring Boot Thymeleaf") String description) {
        request.setAttribute("description", "传值为: " + description);
        return "thymeleaf";     //默认目录是template
    }

    @GetMapping("/attributes")
    public String attributes(HttpServletRequest request){
        request.setAttribute("title", "th 标签演示");
        request.setAttribute("th_id", "th input");
        request.setAttribute("th_name", "th name");
        request.setAttribute("th_value", "noahlin");
        request.setAttribute("th_href", "https://noahlin.top");
        return "th_attribute";
    }

    @GetMapping("/syntax")
    public String syntax(HttpServletRequest request){
        request.setAttribute("thymeleafText", "Laconia");
        request.setAttribute("number", 2021);
        return "th_syntax";
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request){
        request.setAttribute("title", "Thymeleaf Tag works");
        request.setAttribute("testString", "Spring Boot");
        request.setAttribute("bool", "true");
        request.setAttribute("testArray", new Integer[]{2017, 2018, 2019, 2020, 2021});
        request.setAttribute("testList", Arrays.asList("Spring", "SpringBoot", "Thymeleaf", "Mybatis", "Java"));
        Map testMap = new HashMap();
        testMap.put("platform", "Laconia");
        testMap.put("title", "Spring Boot");
        testMap.put("author", "noahlin");
        request.setAttribute("testMap", testMap);
        request.setAttribute("testDate", new Date());
        return "th_test";
    }
}
