package com.heshi.requestParam.controller;

import com.heshi.requestParam.entity.UserDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author by fukun
 */
@RestController
@RequestMapping("/restBase")
public class RequestAttributeRestController {

    private Logger logger = LoggerFactory.getLogger(RequestAttributeRestController.class);

    @ModelAttribute
    public void redirectRequestAttribute(HttpServletRequest request) {
        UserDo userDo = new UserDo();
        userDo.setName("name1");
        userDo.setValid(true);
        userDo.setPermission("r");
        userDo.setPassword("1");
        logger.info("userDo:{}", userDo);
        request.setAttribute("userDo", userDo);
    }

    @GetMapping("/handle1")
    public void handleRequestAttribute(@RequestAttribute Optional<UserDo> userDo, @RequestAttribute("nameFromFilter") Optional<String> nameFromFilter) {
        if (userDo.isPresent()) {
            logger.info("userDo:{}", userDo.get());
        } else {
            logger.info("userDo is null");
        }
        if (nameFromFilter.isPresent()) {
            logger.info("nameFromFilter:{}", nameFromFilter.get());
        } else {
            logger.info("nameFromFilter is null");
        }
    }

    @GetMapping("/handleRedirect")
    public ModelAndView handleRedirect(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("forward:/restBase/handleRedirectGet");
        request.setAttribute("redirectAttributename", "请求转发");
        logger.info("handleRedirect");
        return modelAndView;
    }

    @GetMapping("/handleRedirectGet")
    public void handleRedirectGet(HttpServletRequest request, @RequestAttribute("redirectAttributename") Optional<String> redirectAttributename) {
        if (redirectAttributename.isPresent()) {
            logger.info("redirectAttributename:{}", redirectAttributename.get());
        } else {
            logger.info("redirectAttributename is null");
        }
    }
}
