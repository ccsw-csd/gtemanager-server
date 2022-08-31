package com.ccsw.gtemanager.user;

import org.springframework.data.domain.Page;

import com.ccsw.gtemanager.common.exception.AlreadyExistsException;
import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.user.model.UserDto;
import com.ccsw.gtemanager.user.model.UserEntity;
import com.ccsw.gtemanager.user.model.UserSearchDto;

/**
 * @author ccsw
 *
 */
public interface UserService {

    UserEntity getByUsername(String username);

    Page<UserEntity> findPage(UserSearchDto dto);

    void delete(Long id) throws EntityNotFoundException;

    UserEntity getById(Long id) throws EntityNotFoundException;

    void createUser(UserDto userDto) throws AlreadyExistsException;

}
