package com.ccsw.gtemanager.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.user.model.UserEntity;

import java.util.List;

/**
 * @author ccsw
 *
 */
public interface UserRepository extends CrudRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    /**
     * Recupera un usuario con su username
     *
     * @param username
     * @return
     * @throws Exception
     */
    UserEntity getByUsername(String username);

}
