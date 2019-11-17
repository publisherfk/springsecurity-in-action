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
    public UserDo saveUser(@PathVariable Long id) {
        return userService.get(id);
    }

    @PostMapping("/")
    public UserDo saveUser(@RequestBody UserDo userDo) {
        return userService.save(userDo);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public UserDo putUser(@PathVariable Long id, @RequestBody UserDo userDo) {
        return userService.putUserDo(id, userDo);
    }

    @PatchMapping("/{id}")
    public UserDo patchUser(@PathVariable Long id, @RequestParam String password) {
        return userService.patchUserDo(id, password);
    }
}
