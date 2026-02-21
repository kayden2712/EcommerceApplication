package org.example.ecommerceapplication.common.exception.domain;

import org.example.ecommerceapplication.common.enums.ErrorCode;
import org.example.ecommerceapplication.common.exception.base.BusinessException;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
