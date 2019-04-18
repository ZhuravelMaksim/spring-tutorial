package com.it.app.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private HttpStatus httpStatus;

    private String message;
}
