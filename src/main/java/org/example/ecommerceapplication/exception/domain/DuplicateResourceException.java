package org.example.ecommerceapplication.exception.domain;

import org.example.ecommerceapplication.enums.ErrorCode;
import org.example.ecommerceapplication.exception.base.BusinessException;

public class DuplicateResourceException extends BusinessException {
    public DuplicateResourceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
