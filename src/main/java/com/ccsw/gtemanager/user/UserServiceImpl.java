package com.ccsw.gtemanager.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccsw.gtemanager.common.criteria.TernarySearchCriteria;
import com.ccsw.gtemanager.common.exception.AlreadyExistException;
import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.user.model.UserDto;
import com.ccsw.gtemanager.user.model.UserEntity;
import com.ccsw.gtemanager.user.model.UserSearchDto;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

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
    public void delete(Long id) throws EntityNotFoundException {

        UserEntity userToDelete = getById(id);
        this.userRepository.deleteById(userToDelete.getId());
    }

    @Override
    public UserEntity getById(Long id) throws EntityNotFoundException {

        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    }

    @Override
    public void createUser(UserDto userDto) throws AlreadyExistException {

        this.checkIfValuesAreDuped(userDto);

        UserEntity newUser = null;
        newUser = new UserEntity();

        BeanUtils.copyProperties(userDto, newUser, "id");
        this.userRepository.save(newUser);

    }

    private void checkIfValuesAreDuped(UserDto dto) throws AlreadyExistException {
        Boolean dupeName, dupePriority;

        dupeName = this.userRepository.existsByUsername(dto.getUsername());
        dupePriority = this.userRepository.existsByEmail(dto.getEmail());

        if (dupeName || dupePriority)
            throw new AlreadyExistException();
    }

}
