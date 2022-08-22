package com.ccsw.gtemanager.user;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.user.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    UserEntity getByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
