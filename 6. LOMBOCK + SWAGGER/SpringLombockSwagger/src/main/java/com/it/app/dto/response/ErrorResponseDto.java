package com.it.app.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponseDto {

    @NonNull
    private HttpStatus httpStatus;

    @NonNull
    private String message;
}
