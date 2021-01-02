package com.inspire12.homepage.dto;

import com.inspire12.homepage.exception.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommonException extends RuntimeException {
    private ErrorCode errorCode;
}
