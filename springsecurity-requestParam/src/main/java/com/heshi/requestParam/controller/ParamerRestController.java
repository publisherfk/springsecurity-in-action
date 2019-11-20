package com.heshi.requestParam.controller;

import com.heshi.requestParam.entity.UserDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author by fukun
 */
@RestController
@RequestMapping("/restBase")
@SessionAttributes("userDo")
public class ParamerRestController {
    private Logger logger = LoggerFactory.getLogger(ParamerRestController.class);

    @GetMapping("/testRequestParam")
    public String testRequestParam(@RequestParam String username) {
        logger.info("username:{}", username);
        return username;
    }

    @PostMapping("/testRequestBody")
    public UserDo testRequestBody(@RequestBody UserDo userDo) {
        logger.info("username:{}", userDo);
        return userDo;
    }

    @GetMapping("/handleRequestHeader1")
    public void handleRequestHeader1(@RequestHeader("Accept") Optional<List> accept) {
        if (accept.isPresent()) {
            List accpetList = accept.get();
            accpetList.stream().forEach(object -> {
                logger.info("accept * :{}", object);
            });
        }
    }

    @GetMapping("/handleRequestHeader2")
    public void handleRequestHeader2(@RequestHeader Map<String, String> accept) {
        accept.forEach((k, v) -> logger.info("{} :{}", k, v));
    }

    @RequestMapping(value = "/handleCookieValue")
    public void handleCookieValue(@CookieValue("JSESSIONID") Optional<String> cookie) {
        if (cookie.isPresent()) {
            logger.info(cookie.get());
        }
    }

    @RequestMapping(value = "/handleModelAttribute/{id}/{name}/{password}")
    public void handleModelAttribute(@ModelAttribute UserDo userDo) {
        logger.info("userDo:{}", userDo);
    }

    @ModelAttribute
    public UserDo setUserDo(@RequestParam(value = "id", required = false) Long id) {
        logger.info("id:{}", id);
        UserDo userDo = new UserDo();
        userDo.setId(10000L);
        userDo.setName("heshi");
        userDo.setPassword("11111111");
        userDo.setPermission("r");
        userDo.setValid(true);
        return userDo;
    }

    @RequestMapping(value = "/handleModelAttribute")
    public void handleModelAttribute2(@ModelAttribute UserDo userDo) {
        logger.info("userDo:{}", userDo);
    }

    @RequestMapping(value = "/setSessionAttributes")
    public void setSessionAttributes(@ModelAttribute UserDo userDo, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userDo", userDo);
    }

    @RequestMapping(value = "/getSessionAttributes")
    public void getSessionAttributes(HttpServletRequest request, SessionStatus status) {
        HttpSession session = request.getSession();
        UserDo userDo = (UserDo) session.getAttribute("userDo");
        if (null != userDo) {
            logger.info("session userDo:{}", userDo);
            if (!status.isComplete()) { //if session has marked,return false,else return true
                status.setComplete();
                logger.info("clear session marked");
            } else {
                logger.info("session has'n marked!");
            }
        } else {
            logger.info("session userDo is null");
        }
    }

    @RequestMapping("/handleSessionAttribute")
    public void handleSessionAttribute(@SessionAttribute UserDo userDo) {
        logger.info("handlerSessionAttribute method session userDo:{}", userDo);
    }
}
