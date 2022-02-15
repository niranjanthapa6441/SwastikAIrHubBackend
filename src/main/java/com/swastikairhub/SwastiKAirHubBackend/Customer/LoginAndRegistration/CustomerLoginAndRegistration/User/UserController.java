package com.swastikairhub.SwastiKAirHubBackend.Customer.LoginAndRegistration.CustomerLoginAndRegistration.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public Iterable<User> findAllUser(){
        return  userService.findAllUser();
    }
}
