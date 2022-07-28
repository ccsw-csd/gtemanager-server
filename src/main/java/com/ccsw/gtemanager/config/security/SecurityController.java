package com.ccsw.gtemanager.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.user.UserService;

@RequestMapping(value = "/security/")
@RestController
public class SecurityController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "/user/", method = RequestMethod.GET)
    public UserInfoAppDto get() {

        UserInfoAppDto userInfoAppDto = UserUtils.getUserDetails();

        if (userService.getByUsername(userInfoAppDto.getUsername()) == null) {
            userInfoAppDto.setRole("User");
        }

        return userInfoAppDto;

    }
}
