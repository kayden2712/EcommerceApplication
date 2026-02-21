package org.example.ecommerceapplication.common.exception.domain;

import org.example.ecommerceapplication.common.enums.ErrorCode;
import org.example.ecommerceapplication.common.exception.base.BusinessException;

public class DuplicateResourceException extends BusinessException {
    public DuplicateResourceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
