package org.example.ecommerceapplication.exception.domain;

import org.example.ecommerceapplication.enums.ErrorCode;
import org.example.ecommerceapplication.exception.base.BusinessException;

public class InvalidOperationException extends BusinessException {
    public InvalidOperationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
