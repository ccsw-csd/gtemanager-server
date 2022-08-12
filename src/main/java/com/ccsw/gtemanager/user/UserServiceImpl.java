package com.ccsw.gtemanager.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccsw.gtemanager.common.criteria.TernarySearchCriteria;
import com.ccsw.gtemanager.user.model.UserEntity;
import com.ccsw.gtemanager.user.model.UserSearchDto;

/**
 * @author ccsw
 *
 */

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEntity getByUsername(String username) {

        return this.userRepository.getByUsername(username);
    }

    @Override
    public Page<UserEntity> findPage(UserSearchDto userSearchDto) {

        UserSpecification username = new UserSpecification(
                new TernarySearchCriteria("username", null, null, ":", userSearchDto.getUsername()));

        UserSpecification firstnameLastname = new UserSpecification(
                new TernarySearchCriteria("firstName", "lastName", null, "concat :", userSearchDto.getName()));

        UserSpecification lastnameFirstname = new UserSpecification(
                new TernarySearchCriteria("lastName", "firstName", null, "concat :", userSearchDto.getName()));

        Specification<UserEntity> specification = Specification.where(username)
                .and(firstnameLastname.or(lastnameFirstname));

        return this.userRepository.findAll(specification, userSearchDto.getPageable());
    }

    @Override
    public List<UserEntity> findByFilter(String filter) {

        UserSpecification firstnameLastnameUsername = new UserSpecification(
                new TernarySearchCriteria("firstName", "lastName", "username", "concat concat :", filter));

        Specification<UserEntity> specification = Specification.where(firstnameLastnameUsername);

        return this.userRepository.findAll(specification, PageRequest.of(0, 15)).getContent();
    }
}
