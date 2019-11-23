package com.heshi.requestParam.controller;

import com.heshi.requestParam.entity.UserDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author by fukun
 */
@RestController
@RequestMapping("/restBase")
public class RequestBodyRestController {
    Logger logger = LoggerFactory.getLogger(RequestBodyRestController.class);

    @GetMapping("/requestBodyUserDo")
    public void handle(@RequestBody Optional<UserDo> userDo) {
        if (userDo.isPresent()) {
            logger.info("userDo:{}", userDo.get());
        } else {
            logger.info("userDo is null");
        }
    }

    @GetMapping("/httpEntityUserDo")
    public void handle(HttpEntity<UserDo> userDo) {
        logger.info("userDo:{}", userDo);
    }
}
