package com.heshi.requestParam.controller;

import com.heshi.requestParam.entity.UserDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author by fukun
 */
@Controller
@RequestMapping("/base")
public class ParamerController {
    private Logger logger = LoggerFactory.getLogger(ParamerRestController.class);

    @GetMapping("/testRequestParam")
    public String testRequestParam(@RequestParam Optional<String> username, Model model) {
        String name = null;
        if(username.isPresent()){
            name = username.get();
        }
        logger.info("username:{}", name);
        model.addAttribute("username", name);
        return "base/testRequestParam";
    }

    @PostMapping("/testRequestBody")
    public UserDo testRequestBody(@RequestBody UserDo userDo) {
        logger.info("UserDo:{}", userDo);
        return userDo;
    }

    @PostMapping("/testModelAttribute")
    @ResponseBody
    public UserDo testModelAttribute(@ModelAttribute UserDo userDo) {
        logger.info("UserDo:{}", userDo);
        return userDo;
    }
}
