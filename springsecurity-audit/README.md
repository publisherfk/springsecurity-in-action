#几种RequestMethod的实例及说明
&emsp;&emsp;翻看RequestMethod的源码，我们会发现RequestMethod有八种请求方式。以往的开发中，我们基本上只会用到两种请求方式，put和post。现在使用spring5之后，使用另外几种请求方式会方便很多，按照实际需求，选择合适的method会让我们的接口看起来更规范。
 
```
package org.springframework.web.bind.annotation;
public enum RequestMethod {

	GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE

}
```
&emsp;&emsp;先说第一种，Get请求，Get是指从指定的资源处请求资源，我们常用到的就是查询。

```
package com.heshi.audit.controller;

import com.heshi.audit.entity.UserDo;
import com.heshi.audit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/base")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDo getUser(@PathVariable Long id) {
        return userService.get(id);
    }
}

```
&emsp;&emsp;POST请求，向指定的资源服务器上提交要被处理的资源，常用与表单等的提交。POST请求是非幂等性的。

```
package com.heshi.audit.controller;

import com.heshi.audit.entity.UserDo;
import com.heshi.audit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/base")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public UserDo saveUser(@RequestBody UserDo userDo) {
        return userService.save(userDo);
    }
}
```
&emsp;&emsp;幂等性：一次和多次请求某一个资源对于资源本身应该具有同样的结果，其任意多次执行对资源本身所产生的影响均与一次执行的影响相同。  
&emsp;&emsp;PUT请求，主要用于更新资源，因为主要是更新资源，所以发起PUT请求时，一般需要带上主键。

```
package com.heshi.audit.controller;

import com.heshi.audit.entity.UserDo;
import com.heshi.audit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/base")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/{id}")
    public UserDo putUser(@PathVariable Long id, @RequestBody UserDo userDo) {
        return userService.putUserDo(id, userDo);
    }
}
```
&emsp;&emsp;DELETE请求，主要删除资源。

```
package com.heshi.audit.controller;

import com.heshi.audit.entity.UserDo;
import com.heshi.audit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/base")
public class UserController {
    @Autowired
    private UserService userService;

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
```
&emsp;&emsp;PATCH请求，更新资源。
```
package com.heshi.audit.controller;

import com.heshi.audit.entity.UserDo;
import com.heshi.audit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/base")
public class UserController {
    @Autowired
    private UserService userService;

    @PatchMapping("/{id}")
    public UserDo patchUser(@PathVariable Long id, @RequestParam String password) {
        return userService.patchUserDo(id, password);
    }
}
```
&emsp;&emsp;PATCH和PUT都是更新资源，这两种请求方式都是幂等性。PUT主要是更新整个资源，PATCH主要是更新部分资源，比如我们更新用户名或者密码时，就可以使用PATCH方式，如果把整个user对象基本上都更新掉，可以使用PUT请求。
&emsp;&emsp;只举了五种方式的实例，以上举例不一定合适。之所以有这么多种实例，也是为了接口规范，方便管理，对于计算机而言，这些请求的处理方式都差不多，所以我们可以根据自身请求选择何时的requestMethod，也不要因为请求多了而解决哪种method，量力而行，适合自己的才是最好的。

&emsp;&emsp;正常情况下，大部分系统里面存在审计日志，记录用户的操作情况，结果一般记录在Mongodb，或者发送到kafka消息中间件中，由日志审计系统处理这些日志信息，我们这边暂时以关系库举例。  
&emsp;&emsp;首先创建一个审计日志实体类  

```
@Entity
@Table(name = AuditLog.TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {
    public static final String TABLE_NAME = "BASE_AUDIT_LOG";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "REQUEST_METHOD")
    private String requestMethod;// 请求方式
    @Column(name = "STATUS")
    private int status;//状态码
    @Column(name = "REQUEST_PATH")
    private String requestPath;
    @Column(name = "MSG")
    private String msg;
    @Column(name = "USER_NAME")
    private String userName;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifyTime;
}

```
&emsp;&emsp;再创建一个拦截器，用于实际记录日志信息：  

```
@Component
public class AuditLogInteceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        AuditLog auditLog = new AuditLog();
        auditLog.setRequestMethod(request.getMethod());
        auditLog.setRequestPath(request.getRequestURI());
        UserDo user = (UserDo) request.getAttribute("user");
        if (null != user) {
            auditLog.setUserName(user.getName());
        } else {
            auditLog.setUserName("匿名登录");
        }
        auditLogRepository.save(auditLog);
        request.setAttribute("auditLogId", auditLog.getId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        Long auditLogId = (Long) request.getAttribute("auditLogId");
        if (null != auditLogId) {
            Optional<AuditLog> optional = auditLogRepository.findById(auditLogId);
            if (optional.isPresent()) {
                AuditLog auditLog = optional.get();
                auditLog.setStatus(response.getStatus());
                auditLogRepository.save(auditLog);
            }
        }
    }
}
```
&emsp;&emsp;这里为什么还需要在afterCompletion方法里面记录返回码呢，在preHandle方法里面，通过request也可以获取返回码，我们这样做的目的是为了防备异常情况，因为controller方法执行结束都会进到afterCompletion方法里面来，通过这种方式，可以完整记录审计日志。
&emsp;&emsp;为了使拦截器生效，我们还需要实现WebMvcConfigurer接口

```
@Configuration
@EnableJpaAuditing
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuditLogInteceptor auditLogInteceptor;

    @Autowired
    private AclInteceptor aclInteceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLogInteceptor);
        registry.addInterceptor(aclInteceptor);
    }
}
```
&emsp;&emsp;以上代码基本上完整的实现了审计日志的功能，但是使用过程中，请求异常的情况下，数据库表里面会生成两条记录，一条是请求的，一条是/error的，这样的日志会影响我们的审计功能，因此我们需要处理下，排除掉/error那条日志。  

```
@RestControllerAdvice
public class ErrorHandler {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handler(HttpServletRequest request, Exception ex) {
        Map<String, Object> info = new HashMap<>();
        info.put("message", ex.getMessage());
        info.put("date", LocalDateTime.now());
        if (null != request) {
            Long auditLogId = (Long) request.getAttribute("auditLogId");
            Optional<AuditLog> optional = auditLogRepository.findById(auditLogId);
            if (optional.isPresent()) {
                AuditLog auditLog = optional.get();
                auditLog.setMsg(ex.getMessage());
                auditLogRepository.save(auditLog);
            }
        }
        return info;
    }
}
```
&emsp;&emsp;如上，通过RestControllerAdvice注解，我们提前将请求退出来实现不记录/error这条日志，controller请求可以参考ControllerAdvice注解来实现。  
&emsp;&emsp;以上这些功能都需要在一个大前提下，请求的调用顺序实际上是：Filter->Interceptor->ControllerAdvice->Aop->Controller,请求执行结束之后，实际上是反过来走的，因此我们在ControolerAdvice上面做了过滤之后不会记录/error日志。

# 基于jpa和ControllerAdvise的审计日志实现  
&emsp;&emsp;正常情况下，大部分系统里面存在审计日志，记录用户的操作情况，结果一般记录在Mongodb，或者发送到kafka消息中间件中，由日志审计系统处理这些日志信息，我们这边暂时以关系库举例。  
&emsp;&emsp;首先创建一个审计日志实体类  

```
@Entity
@Table(name = AuditLog.TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {
    public static final String TABLE_NAME = "BASE_AUDIT_LOG";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "REQUEST_METHOD")
    private String requestMethod;// 请求方式
    @Column(name = "STATUS")
    private int status;//状态码
    @Column(name = "REQUEST_PATH")
    private String requestPath;
    @Column(name = "MSG")
    private String msg;
    @Column(name = "USER_NAME")
    private String userName;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifyTime;
}

```
&emsp;&emsp;再创建一个拦截器，用于实际记录日志信息：  

```
@Component
public class AuditLogInteceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        AuditLog auditLog = new AuditLog();
        auditLog.setRequestMethod(request.getMethod());
        auditLog.setRequestPath(request.getRequestURI());
        UserDo user = (UserDo) request.getAttribute("user");
        if (null != user) {
            auditLog.setUserName(user.getName());
        } else {
            auditLog.setUserName("匿名登录");
        }
        auditLogRepository.save(auditLog);
        request.setAttribute("auditLogId", auditLog.getId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        Long auditLogId = (Long) request.getAttribute("auditLogId");
        if (null != auditLogId) {
            Optional<AuditLog> optional = auditLogRepository.findById(auditLogId);
            if (optional.isPresent()) {
                AuditLog auditLog = optional.get();
                auditLog.setStatus(response.getStatus());
                auditLogRepository.save(auditLog);
            }
        }
    }
}
```
&emsp;&emsp;这里为什么还需要在afterCompletion方法里面记录返回码呢，在preHandle方法里面，通过request也可以获取返回码，我们这样做的目的是为了防备异常情况，因为controller方法执行结束都会进到afterCompletion方法里面来，通过这种方式，可以完整记录审计日志。
&emsp;&emsp;为了使拦截器生效，我们还需要实现WebMvcConfigurer接口

```
@Configuration
@EnableJpaAuditing
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuditLogInteceptor auditLogInteceptor;

    @Autowired
    private AclInteceptor aclInteceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLogInteceptor);
        registry.addInterceptor(aclInteceptor);
    }
}
```
&emsp;&emsp;以上代码基本上完整的实现了审计日志的功能，但是使用过程中，请求异常的情况下，数据库表里面会生成两条记录，一条是请求的，一条是/error的，这样的日志会影响我们的审计功能，因此我们需要处理下，排除掉/error那条日志。  

```
@RestControllerAdvice
public class ErrorHandler {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handler(HttpServletRequest request, Exception ex) {
        Map<String, Object> info = new HashMap<>();
        info.put("message", ex.getMessage());
        info.put("date", LocalDateTime.now());
        if (null != request) {
            Long auditLogId = (Long) request.getAttribute("auditLogId");
            Optional<AuditLog> optional = auditLogRepository.findById(auditLogId);
            if (optional.isPresent()) {
                AuditLog auditLog = optional.get();
                auditLog.setMsg(ex.getMessage());
                auditLogRepository.save(auditLog);
            }
        }
        return info;
    }
}
```
&emsp;&emsp;如上，通过RestControllerAdvice注解，我们提前将请求退出来实现不记录/error这条日志，controller请求可以参考ControllerAdvice注解来实现。  
&emsp;&emsp;以上这些功能都需要在一个大前提下，请求的调用顺序实际上是：Filter->Interceptor->ControllerAdvice->Aop->Controller,请求执行结束之后，实际上是反过来走的，因此我们在ControolerAdvice上面做了过滤之后不会记录/error日志。
