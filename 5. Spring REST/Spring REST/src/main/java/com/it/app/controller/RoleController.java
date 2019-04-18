package com.it.app.controller;

import com.it.app.component.LocalizedMessageSource;
import com.it.app.dto.RoleDto;
import com.it.app.model.Role;
import com.it.app.service.RoleService;
import org.dozer.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final Mapper mapper;

    private final RoleService roleService;

    private final LocalizedMessageSource localizedMessageSource;

    public RoleController(Mapper mapper, RoleService roleService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.roleService = roleService;
        this.localizedMessageSource = localizedMessageSource;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RoleDto>> getAll() {
        final List<Role> roles = roleService.findAll();
        final List<RoleDto> roleDtoList = roles.stream()
                .map((role) -> mapper.map(role, RoleDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(roleDtoList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RoleDto> getOne(@PathVariable Long id) {
        final RoleDto roleDto = mapper.map(roleService.findById(id), RoleDto.class);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RoleDto> save(@Valid @RequestBody RoleDto role) {
        final RoleDto roleDto = mapper.map(roleService.save(mapper.map(role, Role.class)), RoleDto.class);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<RoleDto> update(@Valid @RequestBody RoleDto role, @PathVariable Long id) {
        if (!Objects.equals(id, role.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("controller.role.unexpectedId", new Object[]{}));
        }
        final RoleDto roleDto = mapper.map(roleService.update(mapper.map(role, Role.class)), RoleDto.class);
        return new ResponseEntity<>(roleDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        roleService.deleteById(id);
    }
}
