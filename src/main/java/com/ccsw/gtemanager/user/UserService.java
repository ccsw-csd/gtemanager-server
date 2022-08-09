package com.ccsw.gtemanager.user;

import org.springframework.data.domain.Page;

import com.ccsw.gtemanager.user.model.UserEntity;
import com.ccsw.gtemanager.user.model.UserSearchDto;

/**
 * @author ccsw
 *
 */
public interface UserService {

    /**
     * Recupera un usuario con su username
     *
     * @param username
     * @return
     * @throws Exception
     */
    UserEntity getByUsername(String username);

    Page findPage(UserSearchDto dto);

}
