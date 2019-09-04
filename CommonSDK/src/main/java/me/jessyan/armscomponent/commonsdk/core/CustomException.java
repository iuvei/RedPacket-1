package me.jessyan.armscomponent.commonsdk.core;

public class CustomException extends Exception {

    public CustomException() {
        this("服务器异常");
    }

    public CustomException(String message) {
        this(message,null);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
