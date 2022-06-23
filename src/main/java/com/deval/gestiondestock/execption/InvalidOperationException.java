package com.deval.gestiondestock.execption;

import lombok.Getter;

import java.util.List;

public class InvalidOperationException extends RuntimeException {

    @Getter
    private ErrorCodes errorCodes;
    public InvalidOperationException(String msg){
            super(msg);
        }
    public InvalidOperationException(String msg, Throwable cause){
            super(msg, cause);
        }

    public InvalidOperationException(String msg, Throwable cause, ErrorCodes errorCodes){
            super(msg, cause);
            this.errorCodes = errorCodes;
        }

    public InvalidOperationException(String msg,ErrorCodes errorCodes){
            super(msg);
            this.errorCodes = errorCodes;
        }

}
