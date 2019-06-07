package com.it.app.dto.response;

import com.it.app.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;

    private String name;

    private RoleDto role;
}
