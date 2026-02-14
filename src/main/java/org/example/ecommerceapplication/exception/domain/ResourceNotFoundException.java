package org.example.ecommerceapplication.exception.domain;

import org.example.ecommerceapplication.enums.ErrorCode;
import org.example.ecommerceapplication.exception.base.BusinessException;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
