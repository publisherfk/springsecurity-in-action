package com.heshi.requestParam.controller;

import com.heshi.requestParam.entity.UserDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author by fukun
 */
@RestController
@RequestMapping("/public/restBase")
public class SessionAttributeRestController {
    Logger logger = LoggerFactory.getLogger(SessionAttributeRestController.class);

    @RequestMapping("/handleSessionAttribute2")
    public void handleSessionAttribute(@SessionAttribute UserDo userDo) {
        logger.info("handlerSessionAttribute method session userDo:{}", userDo);
    }
}
