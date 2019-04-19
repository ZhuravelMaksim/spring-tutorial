package com.it.app.unit.service;

import com.it.app.component.LocalizedMessageSource;
import com.it.app.model.Role;
import com.it.app.model.User;
import com.it.app.repository.RoleRepository;
import com.it.app.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private LocalizedMessageSource localizedMessageSource;

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void testFindAll() {
        final List<Role> roleList = Collections.singletonList(new Role());
        when(roleRepository.findAll()).thenReturn(roleList);
        assertEquals(roleService.findAll(), roleList);
    }

    @Test
    public void testFindById() {
        final Role role = new Role();
        when(roleRepository.findById(any(Long.class))).thenReturn(Optional.of(role));
        assertEquals(roleService.findById(1L), role);
    }

    @Test
    public void testSave() {
        final Role role = new Role();
        when(roleRepository.saveAndFlush(role)).thenReturn(role);
        assertEquals(roleService.save(role), role);
    }

    @Test
    public void testUpdate() {
        final Role role = new Role();
        role.setId(1L);
        when(roleRepository.saveAndFlush(role)).thenReturn(role);
        assertEquals(roleService.update(role), role);
    }

    @Test
    public void testDelete() {
        final Role role = new Role();
        role.setId(1L);
        doNothing().when(roleRepository).delete(role);
        assertDoesNotThrow(() -> roleService.delete(role));
    }

    @Test
    public void testDeleteById() {
        doNothing().when(roleRepository).deleteById(any(Long.class));
        assertDoesNotThrow(() -> roleService.deleteById(1L));
    }
}
