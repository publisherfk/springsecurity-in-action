# SpringMvc之参数绑定注解详解
&emsp;&emsp;**@RequestParam**注解绑定在Servlet的方法参数里面的请求参数，主要针对查询参数和==form表单==。这里有一点注意事项，如果参数通过Optional包装，@RequestParam参数的required标识自动置为false。  

```
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
}
```
&emsp;&emsp;**@RequestHeader**注解是个方法参数里面的注解，在Controller里面绑定一个请求header。主要使用如下请求头：  

Key  | value
---|---
Host | localhost:8080
Accept | text/html,application/xhtml+xml,application/xml;q=0.9
Accept-Language | fr,en-gb;q=0.7,en;q=0.3
Accept-Encoding | encoding:gzip, deflate, br
Accept-Charset | ISO-8859-1,UTF-8;q=0.7,*;q=0.7
Keep-alive | 300


```
@Controller
@RequestMapping("/base")
public class ParamerController {
    private Logger logger = LoggerFactory.getLogger(ParamerRestController.class);

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
}
```
&emsp;&emsp;**@CookieValue**注解是个方法参数里面的注解，主要使用如下参数：JSESSIONID=415A4AC178C59DACE0B2C9CA727CDD84

```
@Controller
@RequestMapping("/base")
public class ParamerController {
    private Logger logger = LoggerFactory.getLogger(ParamerRestController.class);
    @RequestMapping(value = "/handleCookieValue")
    public void handleCookieValue(@CookieValue("JSESSIONID") Optional<String> cookie) {
        if (cookie.isPresent()) {
            logger.info(cookie.get());
        }
    }
}
```
&emsp;&emsp;**@ModelAttribute**注解用于访问模型中的属性，如果模型不存在的情况，@ModelAttribute注解将初始化这个模型，@ModelAttribute存在如下五种使用场景
- 通过Model添加的模型
- 通过@SessionAttributes注解使用的HTTP session
- Url的路径变量里面，如示例所示
- 调用默认的构造方法
- 调用匹配Servlet request 参数的主构造器
```
@Controller
@RequestMapping("/base")
public class ParamerController {
    private Logger logger = LoggerFactory.getLogger(ParamerRestController.class);
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
}
```
&emsp;&emsp;**@SessionAttributes**注解用于在请求之间的HTTP Servlet会话中存储模型属性。他是一个类级别的注解，通过指定一个controller声明一个session属性，这通常会列出模型属性的名称或模型属性的类型，这些名称或类型应透明地存储在会话中，以便后续的访问请求。

```
@Controller
@RequestMapping("/base")
public class ParamerController {
    private Logger logger = LoggerFactory.getLogger(ParamerRestController.class);
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
}
```
&emsp;&emsp;**@SessionAttribute**注解作用在方法参数上面，用于访问预先存在的session属性。  
&emsp;&emsp;在实际使用场景中，如果需要增加或者删除session属性，可以考虑在Controller的方法里面注入org.springframework.web.context.request.WebRequest 或 javax.servlet.http.HttpSession，如果只是临时在session中存储模型属性，在声明@SessionAttributes注解之后，再使用@SessionAttribute注解，@SessionAttribute和@SessionAttributes可以不在同一个Controller里面。

```
@Controller
@RequestMapping("/base")
public class ParamerController {
    private Logger logger = LoggerFactory.getLogger(ParamerRestController.class);
    @RequestMapping("/handleSessionAttribute")
    public void handleSessionAttribute(@SessionAttribute UserDo userDo) {
        logger.info("handlerSessionAttribute method session userDo:{}", userDo);
    }
}
```
&emsp;&emsp;**@RequestAttribute**注解的用法类似于@SessionAttribute注解的作用。  
&emsp;&emsp;**@RequestAttribute**注解可以获取到拦截器、过滤器、ModelAttribute注解预存的属性，请求转发带过来的，类似于下面的示例。
```
@Controller
@RequestMapping("/base")
public class ParamerController {
    private Logger logger = LoggerFactory.getLogger(ParamerRestController.class);
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
```
&emsp;&emsp;**@RequestBody**注解可以接收请求体的数据，并且通过HttpMessageConverter序列化一个对象。
```
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
}
```
&emsp;&emsp;可以通过在MVC Config 里面配置Message Converters自定义内容转换格式。还可以结合javax.validation.Valid 或者 Spring的 @Validated注解一起使用。  
&emsp;&emsp;HttpEntity使用上和@RequestBody很像，HttpEntity是基于公开的请求和body体创造的容器对象。如下面示例：  
```
@RestController
@RequestMapping("/restBase")
public class RequestBodyRestController {
    Logger logger = LoggerFactory.getLogger(RequestBodyRestController.class);
    @GetMapping("/httpEntityUserDo")
    public void handle(HttpEntity<UserDo> userDo) {
        logger.info("userDo:{}", userDo);
    }
}
```
&emsp;&emsp;**@ResponseBody**注解作用在方法上面，通过HttpMessageConverter序列化返回内容体。  
&emsp;&emsp;**@ResponseBody**注解也可以作用在类上面，写在方法上面的注解可以被所有的controller集成，等同于@RestController注解。