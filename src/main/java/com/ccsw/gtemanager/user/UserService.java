package com.ccsw.gtemanager.user;

import com.ccsw.gtemanager.user.model.UserEntity;

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

}
