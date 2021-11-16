package top.noahlin.astera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.lang3.StringUtils;
import top.noahlin.astera.async.Event;
import top.noahlin.astera.async.EventProducer;
import top.noahlin.astera.async.EventType;
import top.noahlin.astera.service.LoginService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    @Resource
    LoginService loginService;

    @Resource
    EventProducer eventProducer;

    @GetMapping("/reglogin")
    public String login(HttpServletRequest request,
                        @RequestParam(value = "next", required = false) String next) {
        request.setAttribute("next", next);
        return "login";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("next") String next) {
        try {
            Map<String, String> map = loginService.register(username, password);
            if (map.containsKey("success")) {
                request.setAttribute("msg", map.get("success"));
                if (StringUtils.isNotBlank(next)) {
                    request.setAttribute("next", next);
                }
            }else {
                request.setAttribute("msg", map.get("msg"));
            }
            return "login";
        } catch (Exception e) {
            System.out.println("注册异常" + e.getMessage());
            return "login";
        }
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("next") String next) {
        try {
            Map<String, String> map = loginService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);

                eventProducer.fireEvent(new Event(EventType.LOGIN).
                        setExt("username", username).
                        setExt("email", "example@qq.com"));

                if (StringUtils.isNotBlank(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                request.setAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            System.out.println("登录异常" + e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket) {
        loginService.logout(ticket);
        return "redirect:/";
    }
}
