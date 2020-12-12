package com.derick.services.execeptions;

public class DataIntegrityException extends RuntimeException {

    public DataIntegrityException(String message) {
        super(message);
    }
}
