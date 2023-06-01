package com.xuecheng.base.exception;

public class XuechengPlusException extends RuntimeException{
    private String errMessage;

    public XuechengPlusException() {
    }

    public XuechengPlusException(String message) {
        super(message);
        this.errMessage=message;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    // 为了方便抛出异常，定义一个静态方法，就是替换原来的 throw new 语句
    public static void cast(String message){
        throw new XuechengPlusException(message);
    }
    public static void cast(CommonError commonError){
        throw new XuechengPlusException(commonError.getErrMessage());
    }
}
