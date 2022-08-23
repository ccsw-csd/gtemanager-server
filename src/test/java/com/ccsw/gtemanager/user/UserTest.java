package com.ccsw.gtemanager.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.ccsw.gtemanager.common.exception.AlreadyExistException;
import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.user.model.UserDto;
import com.ccsw.gtemanager.user.model.UserEntity;
import com.ccsw.gtemanager.user.model.UserSearchDto;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    public static final Long EXISTS_USER_ID = 6L;
    public static final Long NOT_EXISTS_USER_ID = 7L;
    public static final Long EXISTS_ROLE_ID = 2L;
    private static final String EXISTS_USER_EMAIL = "USER6@USER.COM";
    private static final String EXISTS_USER_USERNAME = "USERNAME6";

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    private UserDto userDto;

    private UserEntity userEntityData;

    @BeforeEach
    public void setUp() {
        this.userDto = new UserDto();
        this.userDto.setUsername("USERNAME6");
        this.userDto.setFirstName("");
        this.userDto.setLastName("");
        this.userDto.setEmail("");
        userEntityData = new UserEntity();
        this.userEntityData.setId(EXISTS_USER_ID);
        this.userEntityData.setUsername("USERNAME6");
        this.userEntityData.setFirstName("");
        this.userEntityData.setLastName("");
        this.userEntityData.setEmail("");
    }

    @Test
    public void findPageShouldReturnUsersPage() {

        UserSearchDto dto = new UserSearchDto();
        dto.setPageable(PageRequest.of(0, 10));

        List<UserEntity> list = new ArrayList<>();
        list.add(mock(UserEntity.class));

        when(userRepository.findAll(any(), eq(dto.getPageable())))
                .thenReturn(new PageImpl<>(list, dto.getPageable(), list.size()));

        Page<UserEntity> page = userServiceImpl.findPage(dto);

        assertNotNull(page);
        assertEquals(1, page.getContent().size());
    }

    @Test
    public void deleteWithExistsIdShouldDelete() throws EntityNotFoundException {

        this.userDto.setId(EXISTS_USER_ID);
        this.userEntityData.setId(EXISTS_USER_ID);

        when(userRepository.findById(EXISTS_USER_ID)).thenReturn(Optional.of(userEntityData));
        this.userServiceImpl.delete(userDto.getId());

        verify(this.userRepository).deleteById(userDto.getId());
    }

    @Test
    public void deleteWithNoExistsIdShouldThrowException() throws EntityNotFoundException {

        this.userDto.setId(NOT_EXISTS_USER_ID);
        this.userEntityData.setId(NOT_EXISTS_USER_ID);

        assertThrows(EntityNotFoundException.class, () -> this.userServiceImpl.delete(NOT_EXISTS_USER_ID));
        verify(this.userRepository, never()).deleteById(NOT_EXISTS_USER_ID);
    }

    @Test
    public void saveNewUserShouldSave() throws AlreadyExistException {
        UserDto dto = new UserDto();

        dto.setEmail("nuevo@gmail.com");
        dto.setFirstName("Nuevo");
        dto.setLastName("User");
        dto.setUsername("nuevo");

        ArgumentCaptor<UserEntity> userEntity = ArgumentCaptor.forClass(UserEntity.class);

        this.userServiceImpl.createUser(dto);
        verify(this.userRepository).save(userEntity.capture());

    }

    @Test
    public void saveNewUserWithExistEmailShouldThrowException() throws AlreadyExistException {
        UserDto dto = new UserDto();

        dto.setEmail(EXISTS_USER_EMAIL);
        dto.setFirstName("Nuevo");
        dto.setLastName("User");
        dto.setUsername("nuevo");

        ArgumentCaptor<UserEntity> userEntity = ArgumentCaptor.forClass(UserEntity.class);

        when(userRepository.existsByEmail(EXISTS_USER_EMAIL)).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> this.userServiceImpl.createUser(dto));

        verify(this.userRepository, never()).save(userEntity.capture());

    }

    @Test
    public void saveNewUserWithExistUsernameShouldThrowException() throws AlreadyExistException {
        UserDto dto = new UserDto();

        dto.setEmail("nuevo@gmail.com");
        dto.setFirstName("Nuevo");
        dto.setLastName("User");
        dto.setUsername(EXISTS_USER_USERNAME);

        ArgumentCaptor<UserEntity> userEntity = ArgumentCaptor.forClass(UserEntity.class);

        when(userRepository.existsByUsername(EXISTS_USER_USERNAME)).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> this.userServiceImpl.createUser(dto));

        verify(this.userRepository, never()).save(userEntity.capture());

    }

}
