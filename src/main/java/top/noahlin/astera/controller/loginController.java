package top.noahlin.astera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.noahlin.astera.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class loginController {
    @Resource
    UserService userService;

    @GetMapping ("/reglogin")
    public String login(HttpServletRequest request){
        return "login";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password) {
        try {
            Map<String, String> map = userService.register(username, password);
            if (map.containsKey("msg")) {
                request.setAttribute("msg", map.get("msg"));
                return "login";
            }
            if(map.containsKey("success")){
                request.setAttribute("msg", map.get("success"));
                return "login";
            }
            return "redirect:/login";
        }
        catch (Exception e){
            System.out.println("注册异常"+e.getMessage());
            return "login";
        }
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password){
    try{
        Map<String, String> map = userService.login(username, password);
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket", map.get("ticket"));
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        }
        else {
            request.setAttribute("msg",map.get("msg"));
            return "login";
        }
    }
    catch (Exception e){
        System.out.println("登录异常"+e.getMessage());
        return "login";
    }
    }
}
