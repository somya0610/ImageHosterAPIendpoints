package com.upgrad.technical.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class UploadFailedException extends Exception {
    //Define two private and final attributes of String type named "code" and "errorMessage"
    private String code;
    private String errorMessage;
    //Also define the constructor for both the attributes

    public UploadFailedException(String code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }
    //Also define getters for both the attributes
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }


}