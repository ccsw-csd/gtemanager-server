package com.ccsw.gtemanager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.user.model.UserDto;
import com.ccsw.gtemanager.user.model.UserSearchDto;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    BeanMapper beanMapper;

    @Autowired
    UserService userService;

    @RequestMapping(path = "/findPage", method = RequestMethod.POST)
    public Page<UserDto> findPage(@RequestBody UserSearchDto dto) {
        return this.beanMapper.mapPage(this.userService.findPage(dto), UserDto.class);
    }
}
