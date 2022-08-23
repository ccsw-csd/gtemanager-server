package com.ccsw.gtemanager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.common.exception.AlreadyExistException;
import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
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

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public void createUser(@RequestBody UserDto userDto) throws AlreadyExistException {
        this.userService.createUser(userDto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") Long id) throws EntityNotFoundException {
        this.userService.delete(id);
    }
}
