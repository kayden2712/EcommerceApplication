package org.example.ecommerceapplication.exception.security;

import org.example.ecommerceapplication.enums.ErrorCode;
import org.example.ecommerceapplication.exception.base.BusinessException;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
