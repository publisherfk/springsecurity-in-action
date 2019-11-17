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
