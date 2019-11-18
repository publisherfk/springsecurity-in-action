package com.heshi.customlogin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by fukun
 */
@Controller
public class CustomLoginController {
    private Logger logger = LoggerFactory.getLogger(CustomLoginController.class);

    @GetMapping("/oauth/login")
    public String login(HttpServletRequest request) {
//        if (null != request.getSession(false)) {
//            logger.info("============session有效====");
//            return "redirect:/";
//        }
        return "oauth/login";
    }

    @GetMapping("/oauth/logout")
    @ResponseBody
    public void logout(HttpServletRequest request) {
        logger.info("logger.info  注销操作");
        System.out.println("System  注销操作");
        request.getSession().invalidate();
    }
}
