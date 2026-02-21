package org.example.ecommerceapplication.common.exception.domain;

import org.example.ecommerceapplication.common.enums.ErrorCode;
import org.example.ecommerceapplication.common.exception.base.BusinessException;

public class InvalidOperationException extends BusinessException {
    public InvalidOperationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
