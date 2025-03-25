package org.invoice.expection;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
