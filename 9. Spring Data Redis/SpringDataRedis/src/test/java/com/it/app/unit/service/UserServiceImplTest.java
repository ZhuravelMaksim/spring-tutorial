package com.it.app.unit.service;

import com.it.app.component.LocalizedMessageSource;
import com.it.app.model.Role;
import com.it.app.model.User;
import com.it.app.repository.UserRepository;
import com.it.app.service.RoleService;
import com.it.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;


    @Test
    public void testFindAll() {
        final List<User> userList = Collections.singletonList(new User());
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(userService.findAll(), userList);
    }

    @Test
    public void testFindById() {
        final User user = new User();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        assertEquals(userService.findById(1L), user);
    }

    @Test
    public void testSave() {
        final User user = new User();
        final Set<Role> roleSet = getRoles();
        user.setRoles(roleSet);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(roleService.findById(1L)).thenReturn(roleSet.iterator().next());
        assertEquals(userService.save(user), user);
    }

    @Test
    public void testUpdate() {
        final User user = new User();
        user.setId(1L);
        final Set<Role> roleSet = getRoles();
        user.setRoles(roleSet);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleService.findById(1L)).thenReturn(roleSet.iterator().next());
        assertEquals(userService.update(user), user);
    }

    @Test
    public void testDelete() {
        final User user = new User();
        user.setId(1L);
        doNothing().when(userRepository).delete(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.delete(user));
    }

    @Test
    public void testDeleteById() {
        final User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(any(Long.class));
        assertDoesNotThrow(() -> userService.deleteById(1L));
    }

    private Set<Role> getRoles() {
        return Stream.of(1L).map(roleId -> {
            Role role = new Role();
            role.setId(roleId);
            role.setName("user");
            return role;
        }).collect(Collectors.toSet());
    }
}
