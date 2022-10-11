package com.amaris.paypal.paypal.error;

import lombok.Getter;

public class ApplicationException extends RuntimeException  {

    @Getter
    private CustomError customError;

    public ApplicationException(CustomError customError){
        super();
        this.customError = customError;
    }
}