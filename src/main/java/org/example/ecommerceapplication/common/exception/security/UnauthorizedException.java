package org.example.ecommerceapplication.common.exception.security;

import org.example.ecommerceapplication.common.enums.ErrorCode;
import org.example.ecommerceapplication.common.exception.base.BusinessException;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
